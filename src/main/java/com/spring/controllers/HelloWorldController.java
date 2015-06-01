package com.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.models.MyModel;

public class HelloWorldController {
	
	private AuthenticationManager authenticationManager;

	@RequestMapping(value = "/api/authtoken", method = RequestMethod.POST)
	public @ResponseBody String authenticate() {
		System.out.println("POST authenticate()");
		if(this.getAuthenticationManager() == null)
			System.out.println("Auth Manager NULL");
		else {
			System.out.println("Auth Manager NOT NULL");
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("mkyong", "123456");
			Authentication auth = null;
			try {
				auth = getAuthenticationManager().authenticate(authentication);
				if(auth != null) {
					System.out.println("auth returned NOT NULL");
					System.out.println("auth isAuthenticated: " + auth.isAuthenticated());
				}
				else {
				System.out.println("auth returned NULL");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return "{\"authtoken\":\"abc123\"}";
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String hello() {
		System.out.println("POST authenticate()");
		
		return "{\"test\":\"value\"}";
	}
	
	@RequestMapping(value = "/helloget", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public @ResponseBody MyModel testGet() {
		MyModel model = new MyModel();
		model.setName("Nick");
		return model;
	}
	
	@RequestMapping(value = "/helloget/dne", method = RequestMethod.GET)
	public ResponseEntity<MyModel> testGetDNE() {
		MyModel model = new MyModel();
		model.setName("Nick");
		
		return new ResponseEntity<MyModel>(model, HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="hellopost", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<MyModel> testPost() {
		MyModel model = new MyModel();
		model.setName("Nick - HelloPost");
		
		return new ResponseEntity<MyModel>(model, HttpStatus.OK);
	}
	
	@RequestMapping(value="hellopost/dne", method = RequestMethod.POST)
	public ResponseEntity<MyModel> testPostError() {
		MyModel model = new MyModel();
		model.setName("Nick");
		
		return new ResponseEntity<MyModel>(model, HttpStatus.NOT_FOUND);
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
