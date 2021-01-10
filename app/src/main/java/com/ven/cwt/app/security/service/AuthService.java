package com.ven.cwt.app.security.service;

public interface AuthService {

	boolean validateSession(String user, String pass);

}
