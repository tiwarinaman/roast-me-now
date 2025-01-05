package com.naman.roastmenow.ratelimiter;

import com.naman.roastmenow.util.Constants;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class RateLimiter {

    private static final int REQUEST_LIMIT = 3; // Max requests
    private static final Duration REFILL_DURATION = Duration.ofMinutes(1); // Time window

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean isRequestAllowed(String clientIp) {
        Bucket bucket = getBucket(clientIp);
        boolean allowed = bucket.tryConsume(1);
        logTokenDetails(clientIp, bucket);
        return allowed;
    }

    public void addRateLimitingHeaders(HttpServletResponse response, String clientIp) {
        Bucket bucket = getBucket(clientIp);
        response.setHeader(Constants.Header.X_RATE_LIMIT, String.valueOf(REQUEST_LIMIT));
        response.setHeader(Constants.Header.X_RATE_LIMIT__REMAINING, String.valueOf(bucket.getAvailableTokens()));
        response.setHeader(Constants.Header.X_RATE_LIMIT__RESET, String.valueOf(REFILL_DURATION.getSeconds()));
    }

    private Bucket getBucket(String clientIp) {
        return buckets.computeIfAbsent(clientIp, ip -> createNewBucket());
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(REQUEST_LIMIT, Refill.greedy(REQUEST_LIMIT, REFILL_DURATION));
        return Bucket4j.builder().addLimit(limit).build();
    }

    private void logTokenDetails(String clientIp, Bucket bucket) {
        log.info("IP: {}, Tokens available: {}, Capacity: {}", clientIp, bucket.getAvailableTokens(), REQUEST_LIMIT);
    }

}
