package com.dshard.freespace.auth;

import com.dshard.freespace.auth.model.AuthenticationRequest;
import com.dshard.freespace.auth.model.AuthenticationResponse;
import com.dshard.freespace.auth.model.RegisterRequest;
import com.dshard.freespace.model.User;
import com.dshard.freespace.persistance.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            return ResponseEntity.ok(authenticationService.register(registerRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already in use");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/me")
    public User getMe() {
        String currentPrincipalName = authenticationService.getPrincipalName();
        System.out.println(userRepository.findByUsername(currentPrincipalName).orElseThrow());
        return userRepository.findByUsername(currentPrincipalName).orElseThrow();
    }
}
