package com.spring.restfulsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class RestfulAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {

	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("AUTH SUCCESS!!!!");
		request.getRequestDispatcher(determineTargetUrl(request, response)).forward(request, response);
	}
	
	 protected String determineTargetUrl(HttpServletRequest request,
	            HttpServletResponse response) {
	        String context = request.getContextPath();
	        String fullURL = request.getRequestURI();
	        String url = fullURL.substring(fullURL.indexOf(context)+context.length());
	        return url;
	    }

}
