package com.example.concert.config.ratelimiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService {

    private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String key, int limit, int durationInSeconds) {
        return bucketCache.computeIfAbsent(key, k -> {
            Bandwidth limitConfig = Bandwidth.classic(limit, Refill.greedy(limit, Duration.ofSeconds(durationInSeconds)));
            return Bucket4j.builder().addLimit(limitConfig).build();
        });
    }
}
