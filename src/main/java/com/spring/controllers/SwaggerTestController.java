package com.spring.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.models.ServiceAddressResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RequestMapping(value = "/serviceaddresses")
@RestController
@Api(value = "/serviceaddresses", description = "This API is used to retrieve the Service Addresses in the footprint.")
public class SwaggerTestController {

	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "Get all service addresses", notes = "Use this method to retrieve all of the Service Addresses in the footprint.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Service Addresses were found."),
			@ApiResponse(code = 404, message = "Service Addresses were not found.")
	})
	public @ResponseBody ServiceAddressResponse getServiceAddress() {
		ServiceAddressResponse serviceAddressResp = new ServiceAddressResponse();
		
		serviceAddressResp.setStreet1("73 Atlas Avenue");
		serviceAddressResp.setCity("Malverne");
		
		return serviceAddressResp;
	}
}
