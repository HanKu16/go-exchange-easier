package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.auth.LoginApiDocs;
import com.go_exchange_easier.backend.annoations.docs.auth.RegisterApiDocs;
import com.go_exchange_easier.backend.dto.auth.LoginRequest;
import com.go_exchange_easier.backend.dto.auth.TokenBundle;
import com.go_exchange_easier.backend.dto.auth.UserRegistrationRequest;
import com.go_exchange_easier.backend.dto.auth.UserRegistrationResponse;
import com.go_exchange_easier.backend.service.AuthService;
import com.go_exchange_easier.backend.service.UserRegistrar;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Operations related to " +
        "registration and login user for the first time.")
public class AuthController {

    private final UserRegistrar userRegistrar;
    private final AuthService authService;

    @PostMapping("/register")
    @RegisterApiDocs
    public ResponseEntity<UserRegistrationResponse> register(
            @RequestBody @Valid UserRegistrationRequest request) {
        UserRegistrationResponse response = userRegistrar.register(request);
        URI locationUri = URI.create("/api/users/" + response.userId());
        return ResponseEntity.created(locationUri).body(response);
    }

    @PostMapping("/login")
    @LoginApiDocs
    public ResponseEntity<TokenBundle> login(
            @RequestBody @Valid LoginRequest request) {
        TokenBundle response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}
