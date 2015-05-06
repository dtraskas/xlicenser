/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.dao;

import java.util.List;

import com.xinferin.model.Activation;

public interface DAOActivation {
	public int add(Activation activation);
	
	public int getCount(int licenceId);
	
	public List<Activation> list();
}
