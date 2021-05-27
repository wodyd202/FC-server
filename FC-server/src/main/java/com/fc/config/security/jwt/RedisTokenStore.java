package com.fc.config.security.jwt;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fc.config.security.jwt.api.JwtToken;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisTokenStore implements TokenStore{
	private final RedisTemplate<String, Object> template;
	
	@Value("${redis.token-key}")
	private String TOKEN_KEY;
	
	public void save(String userPk, JwtToken token) {
		template.opsForValue().set(TOKEN_KEY + userPk, token, Duration.ofHours(1L));
	}

	@Override
	public Optional<JwtToken> findByUser(String userPk) {
		Object result = template.opsForValue().get(TOKEN_KEY + userPk);
		return Optional.ofNullable((JwtToken) result);
	}

	@Override
	public boolean existByUser(String userPk) {
		return this.findByUser(userPk).isPresent();
	}
	
}
