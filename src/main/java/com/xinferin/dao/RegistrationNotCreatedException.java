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

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="The specified registration cannot be created!")
public class RegistrationNotCreatedException extends Exception { 
	private static final long serialVersionUID = 8090344305523186411L;

	public RegistrationNotCreatedException(){
		super();
	}
}
