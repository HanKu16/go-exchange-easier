package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.auth.LoginRequest;
import com.go_exchange_easier.backend.dto.auth.TokenBundle;
import com.go_exchange_easier.backend.exception.InvalidPrincipalTypeException;
import com.go_exchange_easier.backend.exception.domain.auth.DeviceMismatchException;
import com.go_exchange_easier.backend.exception.domain.auth.TokenExpiredException;
import com.go_exchange_easier.backend.exception.domain.auth.TokenNotFoundException;
import com.go_exchange_easier.backend.exception.domain.auth.TokenRevokedException;
import com.go_exchange_easier.backend.model.RefreshToken;
import com.go_exchange_easier.backend.model.User;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.repository.RefreshTokenRepository;
import com.go_exchange_easier.backend.security.JwtConfig;
import com.go_exchange_easier.backend.service.AuthService;
import com.go_exchange_easier.backend.security.jwt.JwtTokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import java.time.OffsetDateTime;
import java.util.Optional;
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
    public TokenBundle login(LoginRequest request, HttpServletRequest servletRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(), request.password()));
        if (authentication.getPrincipal() instanceof UserCredentials credentials) {
            String accessToken = jwtTokenGenerator.generateAccessToken(credentials);
            String refreshToken = jwtTokenGenerator.generateRefreshToken();
            refreshTokenRepository.save(createNewRefreshToken(
                    credentials.getUser(), refreshToken, servletRequest));
            return new TokenBundle(credentials.getUser()
                    .getId(), accessToken, refreshToken);
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
                    "usage token" + refreshToken + "that is revoked. " +
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
        refreshTokenRepository.save(oldToken);
        refreshTokenRepository.save(newToken);
        return new TokenBundle(user.getId(), newAccessToken, newRawRefreshToken);
    }

    @Override
    @Transactional
    public TokenBundle logout(String refreshToken) {
        String newAccessToken = "";
        String newRefreshToken = "";
        Optional<RefreshToken> optionalOldRefreshToken = refreshTokenRepository
                .findByHashedToken(getHashedToken(refreshToken));
        if (optionalOldRefreshToken.isEmpty()) {
            return new TokenBundle(null, newAccessToken, newRefreshToken);
        }
        RefreshToken oldRefreshToken = optionalOldRefreshToken.get();
        oldRefreshToken.setRevoked(true);
        refreshTokenRepository.save(oldRefreshToken);
        return new TokenBundle(oldRefreshToken.getUser().getId(),
                newAccessToken, newRefreshToken);
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
        refreshToken.setDeviceName(servletRequest.getHeader("User-Agent"));
        refreshToken.setIpAddress(getClientIp(servletRequest));
        refreshToken.setUser(user);
        return refreshToken;
    }

    private String getClientIp(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Forwarded-For");
        if (remoteAddr == null || remoteAddr.isEmpty()) {
            remoteAddr = request.getHeader("X-Real-IP");
        }
        if (remoteAddr == null || remoteAddr.isEmpty()) {
            remoteAddr = request.getRemoteAddr();
        }
        if (remoteAddr != null && remoteAddr.contains(",")) {
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
        if (tokenDeviceId != null && !tokenDeviceId.equals(headerDeviceId)) {
            throw new DeviceMismatchException("Token bound to device " + tokenDeviceId +
                    " but request came from " + headerDeviceId);
        }
        if (headerDeviceId == null) {
            throw new DeviceMismatchException("Missing Device-ID header");
        }
    }

}
