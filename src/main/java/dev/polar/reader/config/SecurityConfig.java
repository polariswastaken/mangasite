package dev.polar.reader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Tells Spring Boot to stop using its default, random-password configuration and look at these rules instead!

@Configuration // = This class contains methods that create beans (managed objects) for the application context
@EnableWebSecurity // Disables spring boots default autoconfiguration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.
                cors(Customizer.withDefaults()).
                csrf(csrf -> csrf.disable()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                authorizeHttpRequests(auth -> auth.
                        requestMatchers("api/auth/**").permitAll()    // <-- Everyone has access
                        .anyRequest().authenticated());                 // <-- Users ONLY have access to all endpoints

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}