package dev.polar.reader.config;

import dev.polar.reader.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//// Tells Spring Boot to stop using its default, random-password configuration and look at these rules instead!
//
//@Configuration // = This class contains methods that create beans (managed objects) for the application context
//@EnableWebSecurity // Disables spring boots default autoconfiguration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
//        http.
//                cors(Customizer.withDefaults()).
//                csrf(csrf -> csrf.disable()).
//                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
//                authorizeHttpRequests(auth -> auth. anyRequest().permitAll());
//                        //requestMatchers("/api/auth/**").permitAll()    // <-- Everyone has access
//                        //.anyRequest().authenticated());                 // <-- Users ONLY have access to all endpoints
//
//        return http.build();
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    // Inject custom filter
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()) // Disable CSRF since we're not using cookies

                // The server does not care who you are, you just have to send a token with each request
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth // Define which endpoints are accessible, also runs last/late
                        .requestMatchers("/api/auth/**").permitAll() // Allow registration/login without tokens
                        .requestMatchers(HttpMethod.GET, "/api/manga/**", "/api/chapters/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/manga/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                // Tells Spring to run our custom JWT filter BEFORE its standard username/password filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // This is Spring's internal engine that handles verification. We need to expose it
    // so we can use it inside our AuthController to check login credentials.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}