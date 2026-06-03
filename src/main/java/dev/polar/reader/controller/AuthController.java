package dev.polar.reader.controller;

import dev.polar.reader.dto.AuthResponse;
import dev.polar.reader.dto.LoginRequest;
import dev.polar.reader.dto.RegisterRequest;
import dev.polar.reader.model.User;
import dev.polar.reader.repository.UserRepository;
import dev.polar.reader.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"https://binje.dev", "http://localhost:8080"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Check if username is already taken
        if(userRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // Create a new User object
        User newUser = new User();
        newUser.setUsername(request.username());
        // Scramble the password before putting it in the database
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole("ROLE_USER"); // Give them a default role

        userRepository.save(newUser);

        // Generate a token for the brand new user so they are instantly logged in
        String token = jwtService.generateToken(newUser.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // This command forces Spring's AuthenticationManager to run into the database,
        // grab the hashed password via CustomUserDetailsService, and check if it matches the text password.
        // If it fails, Spring will throw an exception and block the user here.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        // If we reached this line, the login was successful! Give them a token.
        String token = jwtService.generateToken(request.username());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
