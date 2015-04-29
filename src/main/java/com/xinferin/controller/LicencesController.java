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

import com.xinferin.config.*;

@RestController
@RequestMapping("/licences")
public class LicencesController {

	@Autowired
	private MvcConfiguration config = new MvcConfiguration();
	
	@RequestMapping(value = "/activate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)	
	public void activate(String machineId, String licenceKey){
		
	}
}
