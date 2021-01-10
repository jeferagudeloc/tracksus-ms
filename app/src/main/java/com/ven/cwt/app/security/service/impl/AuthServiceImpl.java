package com.ven.cwt.app.security.service.impl;

import org.springframework.stereotype.Service;

import com.ven.cwt.app.security.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

	@Override
	public boolean validateSession(String user, String pass) {
		return true;
	}

}
