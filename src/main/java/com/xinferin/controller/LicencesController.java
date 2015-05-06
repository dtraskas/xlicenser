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
import com.xinferin.dao.DAOLicence;
import com.xinferin.model.KeyRequest;
import com.xinferin.model.LicenceReply;

@RestController
@RequestMapping("/licences")
public class LicencesController {

	@Autowired
	private MvcConfiguration config = new MvcConfiguration();
	
	@Autowired	
	private DAOLicence daoLicence = config.getDAOLicence();	
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes="application/json")
	@ResponseStatus(HttpStatus.OK)	
	public LicenceReply get(@RequestBody KeyRequest keyRequest){
		return daoLicence.get(keyRequest);
	}
	
	@RequestMapping(value = "/{licenceId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)	
	public String checkRevoke(@PathVariable int licenceId){
		return daoLicence.checkRevoke(licenceId);
	}
}
