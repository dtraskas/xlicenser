/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.xinferin.config.MvcConfiguration;
import com.xinferin.dao.DAOCustomerRegistration;
import com.xinferin.model.CustomerRegistration;
import com.xinferin.model.KeyRequest;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {
	
	@Autowired
	private MvcConfiguration config = new MvcConfiguration();
	
	@Autowired	
	private DAOCustomerRegistration daoCustomerRegistration = config.getDAOCustomerRegistration();
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)	
	public void register(@RequestBody CustomerRegistration customerRegistration) throws Exception{
		daoCustomerRegistration.register(customerRegistration);
	}
	
	@RequestMapping(value = "/getLicence", method = RequestMethod.POST, consumes="application/json")
	@ResponseStatus(HttpStatus.OK)	
	public String getLicence(@RequestBody KeyRequest keyRequest){
		return daoCustomerRegistration.getLicence(keyRequest);
	} 
}
