package com.spring.restfulsecurity;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

public class RestfulAuthenticationFilter2 extends GenericFilterBean {

	private AuthenticationManager authenticationManager;
	
	private final static List<GrantedAuthority> ROLES_12 = AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO");
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		System.out.println("***ENTERED RESTFUL AUTHENTICATION FILTER 2***");
		HttpServletRequest httpRequest = this.getAsHttpRequest(req);
		
		//Retrieve the Auth Token from the request header.
		String authToken = httpRequest.getHeader("X-Auth-Token");
		System.out.println("***ENTERED RESTFUL AUTHENTICATION FILTER 2 -- AuthToken: "+authToken+"***");

		//If AuthToken does not exist, continue on down the chain
		//Methods that are not secured will be allowed, otherwise the custom
		//Entry point will be executed and return an authorization error.
		if (authToken != null) {

			//TODO: If authToken exists, validate.
			//IF valid authToken, create the "authentication" (i.e. Spring's UsernamePasswordAuthenticationToken)
			//and set it in the Security context.
			//ELSE return authorization error.

			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken("mkyong", "123456");
			Authentication auth = null;
			try {
				auth = this.getAuthenticationManager().authenticate(authentication);
			} catch(Exception e) {
				e.printStackTrace();
			}
			//authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
			
			if(auth != null && auth.isAuthenticated())
				SecurityContextHolder.getContext().setAuthentication(authentication);
		}	

		chain.doFilter(req, resp);
	}
	
	//TODO: Validate an Authentication Token
	//1. 
	
	private HttpServletRequest getAsHttpRequest(ServletRequest request)
	{
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	@Autowired
	@Qualifier(value = "myAuthManager")
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

}
