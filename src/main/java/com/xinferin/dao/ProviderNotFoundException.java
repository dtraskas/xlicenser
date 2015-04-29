/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.dao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="The specified provider does not exist")
public class ProviderNotFoundException extends Exception {
	private static final long serialVersionUID = 5374011288349632567L;
	
	private String provider;
	
	public ProviderNotFoundException(String provider) {
		super();
		this.provider = provider;
	}
	
	public String getProvider() {
		return provider;
	}
}
