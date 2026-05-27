package dev.polar.reader;

import dev.polar.reader.service.CustomUserDetailsService;
import dev.polar.reader.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    // Constructor injection so Spring gives us our utilities
    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException, ServletException {

        // 1. Grab the "Authorization" header from the incoming HTTP request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 2. If the header is missing or doesn't start with "Bearer ", skip this filter.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Pass the request to the next filter
            return;
        }

        // 3. Extract the token (everything after "Bearer ")
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt); // Decode it to find out who they claim to be

        // 4. If we found a username and the user isn't ALREADY logged into Spring Security...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Look them up in our database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 5. If the token's signature is valid and matches the database info...
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Create an internal "Security Pass" for Spring
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. STAMP THE PASS! Put them into Spring's official Security Context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 7. Let the request move forward to your Controller
        filterChain.doFilter(request, response);
    }
}