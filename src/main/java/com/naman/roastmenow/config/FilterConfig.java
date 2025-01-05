package com.naman.roastmenow.config;

import com.naman.roastmenow.filter.IpRateLimitingFilter;
import com.naman.roastmenow.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final RateLimiter rateLimiter;

    @Bean
    public OncePerRequestFilter ipRateLimitingFilter() {
        return new IpRateLimitingFilter(rateLimiter);
    }

}
