package dev.polar.reader.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {

    // This is your cryptographic key. It must be kept secret.
    private final String SECRET_STRING = "your-super-secret-secure-and-very-long-key-1234567890";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_STRING);

    // Manufacture a token string
    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username) // Who owns this token
                .withIssuedAt(new Date()) // When it was created
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Expires in 24 hours
                .sign(algorithm); // Mathematically seal it with our secret key
    }

    // Extract the username from a token string
    public String extractUsername(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token); // This throws an exception if the token was tampered with
        return decodedJWT.getSubject();
    }

    // Check if the token matches the user and hasn't expired
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
            String username = decodedJWT.getSubject();
            boolean isExpired = decodedJWT.getExpiresAt().before(new Date());

            return (username.equals(userDetails.getUsername()) && !isExpired);
        } catch (Exception e) {
            // If the signature is invalid or altered, verify() crashes.
            // We catch it and return false (Access Denied).
            return false;
        }
    }
}