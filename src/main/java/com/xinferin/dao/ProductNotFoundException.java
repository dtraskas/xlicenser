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

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="The specified product does not exist")
public class ProductNotFoundException extends Exception {
	
	private static final long serialVersionUID = -7070968098265344150L;
	private String product;
	
	public ProductNotFoundException(String product){
		super();
		this.product = product;
	}
	
	public String getProduct() {
		return product;
	} 
}
