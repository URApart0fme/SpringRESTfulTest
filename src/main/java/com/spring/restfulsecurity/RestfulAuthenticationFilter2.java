package com.spring.restfulsecurity;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	private final static String AUTH_USERNAME_HEADER = "X-AUTH-USERNAME";
	private final static String AUTH_PASSWORD_HEADER = "X-AUTH-PASSWORD";
	private final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
	
	private final static List<GrantedAuthority> ROLES_12 = AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO");
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		System.out.println("***ENTERED RESTFUL AUTHENTICATION FILTER 2***");
		HttpServletRequest httpRequest = this.getAsHttpRequest(req);
		HttpServletResponse httpResponse = this.getAsHttpResponse(resp);
		
		//1. First we should check if a username & password exists, if so, validate.
		//   if the username and password are valid we can return a new auth token.
		boolean authenticated = checkUsernamePassword(httpRequest, httpResponse);
		
		//2. If username & password does not exist or is not valid then check the auth token.
		if(!authenticated) {
			authenticated = checkToken(httpRequest, httpResponse);
		}
		
		//3. Always continue down the chain. The checkUsernamePassword and checkToken
		//   methods will handle the setting of the user in the SecurityContext.  If both
		//   failed authentication there will be no user in the SecurityContext and if the backend
		//   process trying to be reached does not allow anonymous, then user will be given an error code
		//   of unauthorized.
		chain.doFilter(req, resp);
	}
	
	private boolean checkUsernamePassword(HttpServletRequest httpRequest, HttpServletResponse response) {
		
		//Retrieve the credentials from the request header.
		String username = httpRequest.getHeader(AUTH_USERNAME_HEADER);
		String password = httpRequest.getHeader(AUTH_PASSWORD_HEADER);
		
		//If either do not exist, then cannot authenticate via username & password
		if(username == null || password == null)
			return false;
		
		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(username, password);
		Authentication auth = null;
		try {
			auth = this.getAuthenticationManager().authenticate(authentication);
		} catch(Exception e) {
			e.printStackTrace();
		}
		//authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
		
		if(auth != null && auth.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return true;
		}
		
		return false;
	}
	
	private boolean checkToken(HttpServletRequest httpRequest, HttpServletResponse response) {
		
		//Retrieve the Auth Token from the request header.
		String authToken = httpRequest.getHeader("X-Auth-Token");
		System.out.println("***ENTERED RESTFUL AUTHENTICATION FILTER 2 -- AuthToken: "+authToken+"***");
		
		return false;
	}
	
	private HttpServletRequest getAsHttpRequest(ServletRequest request)
	{
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}
	
	private HttpServletResponse getAsHttpResponse(ServletResponse response)
	{
		if (!(response instanceof HttpServletResponse)) {
			throw new RuntimeException("Expecting an HTTP response");
		}

		return (HttpServletResponse) response;
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
