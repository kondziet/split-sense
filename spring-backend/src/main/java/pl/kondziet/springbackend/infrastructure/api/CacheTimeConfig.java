package pl.kondziet.springbackend.infrastructure.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CacheTimeConfig {

    public static final LocalDateTime DEFAULT_CURRENT_TIME = LocalDateTime.now();
    public static final Duration DEFAULT_CACHE_DURATION_MINUTES = Duration.ofMinutes(60);
    private final Duration cacheDuration;

    public CacheTimeConfig(@Value("${exchange-rate.cache-expiration.minutes}") long cacheDuration) {
        this.cacheDuration = Duration.ofMinutes(cacheDuration);
    }

    public Duration getCacheDuration() {
        return cacheDuration;
    }

    public LocalDateTime getCurrentTime() {
        return DEFAULT_CURRENT_TIME;
    }

}
