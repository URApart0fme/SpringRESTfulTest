package com.spring.restfulsecurity;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class RestfulAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private final static List<GrantedAuthority> ROLES_12 = AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO");
	
	public RestfulAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		System.out.println("***ENTERED RESTFUL AUTHENTICATION FILTER -- defaultURL: "+defaultFilterProcessesUrl+"***");
		super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
		setAuthenticationManager(new NoOpAuthenticationManager());
		setAuthenticationSuccessHandler(new RestfulAuthenticationSuccessHandler());
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		super.doFilter(req, resp, filterChain);
		System.out.println("***ENTERED RESTFUL AUTHENTICATION FILTER***");
	}

	
	
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("***ENTERED RESTFUL AUTHENTICATION FILTER -- requiresAuth***");
		boolean result = super.requiresAuthentication(request, response);
		System.out.println("RESULT: " + result);
		return result;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest arg0, HttpServletResponse arg1) throws AuthenticationException,IOException, ServletException {
		System.out.println("***ENTERED RESTFUL AUTHENTICATION FILTER -- attempt authentication***");
		
		if(false)
			throw new AuthenticationServiceException("test");
		else
			return new AnonymousAuthenticationToken("anonymoustest", "anonymoustest", ROLES_12);
	}

}
