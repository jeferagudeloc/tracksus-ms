package com.ven.cwt.app.security;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ven.cwt.app.security.model.UserAuth;
import com.ven.cwt.app.security.service.AuthService;
import com.ven.cwt.app.security.service.JWTService;

@RestController
@RequestMapping("/login")
public class AuthenticateController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateController.class);
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private JWTService jwtService;
	
	
	@Value( "${config.redirect.login}" )
	private String INDEX_HTML_ID;
	
	@Value( "${config.redirect.path}" )
	private String PATH;
	
	@Value( "${config.redirect.port}" )
	private String PORT;
	
	@Value( "${config.redirect.domain}" )
	private String DOMAIN;
	
	@Value( "${config.redirect.httpscheme}" )
	private String HTTP_SCHEME;
	
	@Value( "${config.security.cookie.key}" )
	private String KEY_COOKIE;

	@PostMapping(value = "/redirect", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public void login(HttpServletResponse httpServletResponse, UserAuth auth) throws IOException {

		LOGGER.info("email: {} - pass: {}" , auth.getEmail(), auth.getPassword());
		boolean inDatabase = authService.validateSession(auth.getEmail(), auth.getPassword());
		if (inDatabase) {
			String jwt = jwtService.createJWT(auth.getEmail());
			Cookie cookie = new Cookie(KEY_COOKIE, jwt);
			cookie.setHttpOnly(false);
			cookie.setDomain(DOMAIN);
			cookie.setMaxAge(60 * 60);
			cookie.setPath(PATH);
			httpServletResponse.addCookie(cookie);
			StringBuilder urlRedirect = new StringBuilder();
			urlRedirect.append(HTTP_SCHEME).append(DOMAIN).append(":").append(PORT).append(INDEX_HTML_ID);
			LOGGER.info("Url to redirect: [urlRedirect='{}']", urlRedirect.toString());
			httpServletResponse.sendRedirect(urlRedirect.toString());
		} else {
			httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Hubo  un error al intentar autenticar el usuario");
		}
	}
}
