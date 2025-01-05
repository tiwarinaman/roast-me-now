package com.naman.roastmenow.filter;

import com.naman.roastmenow.ratelimiter.RateLimiter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class IpRateLimitingFilter extends OncePerRequestFilter {

    private static final String[] SWAGGER_PATH_PATTERNS = {
            "/",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/swagger-resources/**",
            "/webjars/**",
            "/swagger-ui/index.html",
            "/v3/api-docs/swagger-config"
    };

    private final RateLimiter rateLimiter;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Check if the current request is for a Swagger endpoint
        if (isSwaggerRequest(request)) {
            log.debug("Swagger request detected, bypassing rate limiting.");
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = request.getRemoteAddr();
        String requestURI = request.getRequestURI();
        log.info("Incoming request from IP: {}, for path: {}", clientIp, requestURI);

        if (rateLimiter.isRequestAllowed(clientIp)) {
            log.debug("Request allowed for IP: {}", clientIp);
            rateLimiter.addRateLimitingHeaders(response, clientIp);
            filterChain.doFilter(request, response);
        } else {
            log.warn("Rate limit exceeded for IP: {}", clientIp);
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests. Please try again later.");
            rateLimiter.addRateLimitingHeaders(response, clientIp);
        }
    }

    private boolean isSwaggerRequest(HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        // Match the request URI with any of the Swagger patterns
        for (String pattern : SWAGGER_PATH_PATTERNS) {
            if (antPathMatcher.match(pattern, requestUri)) {
                return true;
            }
        }
        return false;
    }
}
