/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.dao;

import com.xinferin.model.CustomerRegistration;
import com.xinferin.model.KeyRequest;

public interface DAOCustomerRegistration {
	public void register(CustomerRegistration customerRegistration) throws Exception;	
	
	public String getLicence(KeyRequest keyRequest);
}
