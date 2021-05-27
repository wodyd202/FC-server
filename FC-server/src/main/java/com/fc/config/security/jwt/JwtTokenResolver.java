package com.fc.config.security.jwt;

import javax.servlet.http.HttpServletRequest;

import com.fc.config.security.jwt.SimpleJwtTokenResolver.InvalidJwtTokenException;
import com.fc.config.security.jwt.api.JwtToken;

public interface JwtTokenResolver {
	JwtToken resolve(HttpServletRequest request) throws InvalidJwtTokenException;
}
