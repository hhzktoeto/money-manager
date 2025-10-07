package hhz.ktoeto.moneymanager.backend.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRepository {

    private final RedisTemplate<String, Long> redisTemplate;

    public Optional<Long> get(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public void save(String key, Long value, Duration expiration) {
        redisTemplate.opsForValue().set(key, value, expiration);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}