package com.github.pimvoeten.jpa.example.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

    // TODO: set TTL for caches
}
