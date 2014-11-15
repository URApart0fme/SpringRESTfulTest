package com.spring.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.models.MyModel;

@Controller
public class HelloWorldController {

	@RequestMapping("/hello")
	public @ResponseBody String hello() {
		System.out.println("GOT HERE");
		return "helloworld CONTROLLER";
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
}
