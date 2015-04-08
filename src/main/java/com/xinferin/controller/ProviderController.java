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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xinferin.config.MvcConfiguration;
import com.xinferin.dao.DAOProvider;
import com.xinferin.model.Provider;

@RestController
@RequestMapping("/providers")
public class ProviderController {
	
	@Autowired
	private MvcConfiguration config = new MvcConfiguration();
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Provider> providers() {
		DAOProvider daoProvider = config.getDAOProvider();		
		return daoProvider.list();
	}
}
