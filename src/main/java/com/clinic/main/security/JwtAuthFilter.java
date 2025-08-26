package com.clinic.main.security;

import com.clinic.main.customeExceptions.UserNotFoundException;
import com.clinic.main.entity.User;
import com.clinic.main.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);
    private final AuthTokenization authTokenization;
    private final UserRepository userRepository;

    public JwtAuthFilter(HandlerExceptionResolver handlerExceptionResolver, AuthTokenization authTokenization, UserRepository userRepository) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.authTokenization = authTokenization;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            log.error("incoming request: {}", request.getRequestURI());

            final String requestTokenHandler = request.getHeader("Authorization");

            if (requestTokenHandler == null || !(requestTokenHandler.startsWith("Bearer"))) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = requestTokenHandler.split("Bearer")[1];
            String username = authTokenization.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UserNotFoundException("User invalid"));

                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
