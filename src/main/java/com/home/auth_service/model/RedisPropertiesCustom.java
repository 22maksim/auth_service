package com.home.auth_service.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "spring.data.redis")
public record RedisPropertiesCustom(
        String host,
        String port
) {
}
