package com.fc.config.security.jwt;

import java.util.Optional;

import com.fc.config.security.jwt.api.JwtToken;

public interface TokenStore {
	void save(String userPk, JwtToken token);

	Optional<JwtToken> findByUser(String userPk);
	
	boolean existByUser(String userPk);
}
