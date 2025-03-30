package com.home.auth_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final Long EXPIRATION_TIME =
            System.getenv("EXPIRATION_TIME") != null
                    ? Long.parseLong(System.getenv("EXPIRATION_TIME")) : 3600L;

    public RedisService(@Qualifier("redisTemplateCustom") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void putTokenBlackListByUsername(String username, String token) {
        if (username == null || token == null) {
            throw new IllegalArgumentException("email and token cannot be null");
        }
        if (Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(username, token, EXPIRATION_TIME, TimeUnit.SECONDS))) {
            log.info("Set token blacklist for email: {}", username);
        }

    }

    public boolean existsTokenByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        return redisTemplate.hasKey(username);
    }

    public String getTokenByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        return redisTemplate.opsForValue().get(username);
    }

    public void removeTokenByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if (redisTemplate.delete(username)) {
            log.info("Remove and restored token by username: {}", username);
        }
    }
}
