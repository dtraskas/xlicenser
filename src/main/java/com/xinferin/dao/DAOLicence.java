/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.dao;

import com.xinferin.model.KeyRequest;
import com.xinferin.model.Licence;
import com.xinferin.model.LicenceReply;

public interface DAOLicence {
	public int add(Licence licence);
	
	public void update(Licence licence);
	
	public LicenceReply get(KeyRequest keyRequest);
	
	public String checkRevoke(int licenceId);
}
