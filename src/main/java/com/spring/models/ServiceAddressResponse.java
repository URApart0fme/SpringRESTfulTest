package com.spring.models;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "ServiceAddress", description = "This represents a service address JSON object.")
public class ServiceAddressResponse {

	@ApiObjectField(required = true)
	private String street1;
	
	@ApiObjectField
	private String city;

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
}
