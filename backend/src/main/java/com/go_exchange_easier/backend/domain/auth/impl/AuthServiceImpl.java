package com.go_exchange_easier.backend.domain.auth.impl;

import com.go_exchange_easier.backend.domain.auth.AuthService;
import com.go_exchange_easier.backend.domain.auth.RefreshToken;
import com.go_exchange_easier.backend.domain.auth.RefreshTokenRepository;
import com.go_exchange_easier.backend.domain.auth.UserCredentials;
import com.go_exchange_easier.backend.domain.auth.dto.LoginRequest;
import com.go_exchange_easier.backend.domain.auth.dto.TokenBundle;
import com.go_exchange_easier.backend.domain.auth.dto.LoginSummary;
import com.go_exchange_easier.backend.domain.user.dto.SignedInUserSummary;
import com.go_exchange_easier.backend.domain.auth.InvalidPrincipalTypeException;
import com.go_exchange_easier.backend.domain.auth.DeviceMismatchException;
import com.go_exchange_easier.backend.domain.auth.TokenExpiredException;
import com.go_exchange_easier.backend.domain.auth.TokenNotFoundException;
import com.go_exchange_easier.backend.domain.auth.TokenRevokedException;
import com.go_exchange_easier.backend.domain.user.User;
import com.go_exchange_easier.backend.infrastracture.security.config.JwtConfig;
import com.go_exchange_easier.backend.infrastracture.security.jwt.JwtTokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final JwtConfig jwtConfig;

    @Override
    @Transactional
    public LoginSummary login(LoginRequest request, HttpServletRequest servletRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(), request.password()));
        if (authentication.getPrincipal() instanceof UserCredentials credentials) {
            String accessToken = jwtTokenGenerator.generateAccessToken(credentials);
            String refreshToken = jwtTokenGenerator.generateRefreshToken();
            refreshTokenRepository.save(createNewRefreshToken(
                    credentials.getUser(), refreshToken, servletRequest));
            return new LoginSummary(
                    new SignedInUserSummary(credentials.getUser().getId(), ""),
                    new TokenBundle(accessToken, refreshToken)
            );
        }
        throw new InvalidPrincipalTypeException("Principal was expected to be of " +
                "type UserCredentials but was not.");
    }

    @Override
    @Transactional
    public TokenBundle refresh(String refreshToken, HttpServletRequest servletRequest) {
        RefreshToken oldToken = refreshTokenRepository
                .findByHashedToken(getHashedToken(refreshToken))
                .orElseThrow(() -> new TokenNotFoundException(
                        "Token " + refreshToken + " was not found."));
        if (oldToken.isRevoked()) {
            throw new TokenRevokedException("There was attempt of " +
                    " usage token " + DigestUtils.sha256Hex(refreshToken) + " that is revoked. " +
                    "Probably someone stole the token.");
        }
        if (oldToken.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new TokenExpiredException("Token " + refreshToken +
                    " expired.");
        }
        validateDeviceMatch(oldToken, servletRequest);
        User user = oldToken.getUser();
        UserCredentials credentials = user.getCredentials();
        String newAccessToken = jwtTokenGenerator.generateAccessToken(credentials);
        String newRawRefreshToken = jwtTokenGenerator.generateRefreshToken();
        RefreshToken newToken = createNewRefreshToken(
                user, newRawRefreshToken, servletRequest);
        oldToken.setRevoked(true);
        refreshTokenRepository.saveAll(List.of(oldToken, newToken));
        return new TokenBundle(newAccessToken, newRawRefreshToken);
    }

    @Override
    @Transactional
    public TokenBundle logout(String refreshToken) {
        String newAccessToken = "";
        String newRefreshToken = "";
        if (refreshToken != null) {
            refreshTokenRepository.revokeByHashedToken(
                    getHashedToken(refreshToken));
        }
        return new TokenBundle(newAccessToken, newRefreshToken);
    }

    private RefreshToken createNewRefreshToken(
            User user, String rawRefreshToken,
            HttpServletRequest servletRequest) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setHashedToken(DigestUtils.sha256Hex(rawRefreshToken));
        refreshToken.setCreatedAt(OffsetDateTime.now());
        refreshToken.setExpiresAt(OffsetDateTime.now()
                .plusSeconds(jwtConfig.getRefreshTokenValidityInSeconds()));
        refreshToken.setRevoked(false);
        refreshToken.setDeviceId(UUID.fromString(servletRequest.getHeader("X-Device-Id")));
        refreshToken.setDeviceName(servletRequest.getHeader("X-Device-Name"));
        refreshToken.setIpAddress(getClientIp(servletRequest));
        refreshToken.setUser(user);
        return refreshToken;
    }

    private String getClientIp(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Forwarded-For");
        if ((remoteAddr == null) || remoteAddr.isEmpty()) {
            remoteAddr = request.getHeader("X-Real-IP");
        }
        if ((remoteAddr == null) || remoteAddr.isEmpty()) {
            remoteAddr = request.getRemoteAddr();
        }
        if ((remoteAddr != null) && remoteAddr.contains(",")) {
            remoteAddr = remoteAddr.split(",")[0].trim();
        }
        return remoteAddr;
    }

    private String getHashedToken(String token) {
        return DigestUtils.sha256Hex(token);
    }

    private void validateDeviceMatch(RefreshToken oldToken, HttpServletRequest request) {
        String headerDeviceId = request.getHeader("X-Device-Id");
        String tokenDeviceId = (oldToken.getDeviceId() != null) ?
                oldToken.getDeviceId().toString() : null;
        if ((tokenDeviceId != null) && !tokenDeviceId.equals(headerDeviceId)) {
            throw new DeviceMismatchException("Token bound to device " + tokenDeviceId +
                    " but request came from " + headerDeviceId);
        }
        if (headerDeviceId == null) {
            throw new DeviceMismatchException("Missing Device-ID header");
        }
    }

}
