package com.ven.cwt.app.security.service;

import io.jsonwebtoken.Claims;

public interface JWTService {

	String createJWT(String username);

	Claims decodeJWT(String jwt);
	
}
