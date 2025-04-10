package bank.indef.authentication.controller;

import bank.indef.authentication.exception.UnauthorizedException;
import bank.indef.authentication.model.CreateUserDto;
import bank.indef.authentication.model.LoginRequest;
import bank.indef.authentication.model.LoginResponse;
import bank.indef.authentication.service.AuthService;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody CreateUserDto request) {
        String token = authService.register(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @GetMapping("/logout")
    @SneakyThrows
    public ResponseEntity<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return ResponseEntity.ok(authService.logout(auth, token));
        }
        throw new UnauthorizedException("Invalid Authorization header");
    }
}

