package com.spring.controllers;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.models.ServiceAddressResponse;

@Api(name = "Service Addresses", description = "Service for retrieving addresses in the footprint.")
@RequestMapping(value = "/serviceaddresses")
@RestController
public class JSONDocTestController {
	
	@ApiMethod(description = "Retrieve all service addresses.")
	@ApiErrors(apierrors = { @ApiError(code = "404", description = "services not found.")})
	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ApiResponseObject @ResponseBody ServiceAddressResponse getServiceAddress() {
		ServiceAddressResponse serviceAddressResp = new ServiceAddressResponse();
		
		serviceAddressResp.setStreet1("100 Atlas Avenue");
		serviceAddressResp.setCity("Malverne");
		
		return serviceAddressResp;
	}
}
