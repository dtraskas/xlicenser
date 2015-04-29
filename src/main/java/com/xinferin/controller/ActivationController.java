/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.xinferin.config.MvcConfiguration;
import com.xinferin.dao.DAOActivation;
import com.xinferin.model.Activation;

@RestController
@RequestMapping("/activations")
public class ActivationController {
	@Autowired
	private MvcConfiguration config = new MvcConfiguration();
	
	@Autowired	
	private DAOActivation daoActivation = config.getDAOActivation();
	
	@RequestMapping(value = "/activate", method = RequestMethod.POST, consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void activate(@RequestBody Activation activation){
		daoActivation.add(activation);		
	}
	
	@RequestMapping(method = RequestMethod.GET)	
	public List<Activation> activations() {				
		return daoActivation.list();
	}
}
