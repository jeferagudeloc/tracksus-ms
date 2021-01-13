package com.ven.cwt.app.conf;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ven.cwt.app.security.service.JWTService;

public class CorsFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CorsFilter.class);
	
	@Autowired
	JWTService jwtService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request= (HttpServletRequest) servletRequest;

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "true");
        
        String headerJWT = request.getHeader("user_session");
        
        if (headerJWT==null) {
        	headerJWT = getCookieValue(request.getCookies(), "user_session");
		}
        
        LOGGER.info("Security Token : {}", headerJWT);
        
        
        String url = null;
        if (request instanceof HttpServletRequest) {
        	url = ((HttpServletRequest)request).getRequestURL().toString();
        }
        
		String redirectURL =  "/login/redirect";
		
		if (!url.contains(redirectURL) && headerJWT!=null) {
			try {
				jwtService.decodeJWT(headerJWT);
				filterChain.doFilter(servletRequest, servletResponse);
			} catch (Exception e) {
				throw new ServletException("Error al momento de verificar", e);
			}
				
		} else if (url.contains(redirectURL)) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			throw new ServletException("Acceso denegado - 403");
		}


    }
    
	private String getCookieValue(Cookie[] cookies, String cookieName) {
		System.out.println("cookies");
		System.out.println(cookies);
	    return Arrays.stream(cookies)
	            .filter(c -> c.getName().equals(cookieName))
	            .findFirst()
	            .map(Cookie::getValue)
	            .orElse(null);
	}

    @Override
    public void destroy() {

    }
}