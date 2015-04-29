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

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="The specified customer cannot be created!")
public class CustomerNotCreatedException extends Exception { 
	private static final long serialVersionUID = 3743678621227623970L;

	public CustomerNotCreatedException(){
		super();
	}
}
