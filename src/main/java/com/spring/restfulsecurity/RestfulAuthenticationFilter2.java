package com.spring.restfulsecurity;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

public class RestfulAuthenticationFilter2 extends GenericFilterBean {

	private final static List<GrantedAuthority> ROLES_12 = AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO");
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		System.out.println("***ENTERED RESTFUL AUTHENTICATION FILTER 2***");
		HttpServletRequest httpRequest = this.getAsHttpRequest(req);
		//String authToken = this.extractAuthTokenFromRequest(httpRequest);\
		String userNameHeader = httpRequest.getHeader("X-User-Name");
		System.out.println("***ENTERED RESTFUL AUTHENTICATION FILTER 2 -- UserName: "+userNameHeader+"***");

		if (userNameHeader != null) {

			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken("user1", null, ROLES_12);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}	

		chain.doFilter(req, resp);

	}
	
	private HttpServletRequest getAsHttpRequest(ServletRequest request)
	{
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}

}
