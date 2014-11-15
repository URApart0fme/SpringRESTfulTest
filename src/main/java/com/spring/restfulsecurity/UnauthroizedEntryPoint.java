package com.spring.restfulsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class UnauthroizedEntryPoint implements AuthenticationEntryPoint {

	/**
	 * Rejects all requests.  Login-like function is featured in TokenAuthenticationFilter and this does not
	 * perform or suggests any redirection.  This object is hit whenever user is not authorized (anonymous)
	 * and secured resource is requested.
	 */
	public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException authException) throws IOException, ServletException {
		System.out.println("***Entered UnauthorizedEntryPoint.commence***");
		resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

}
