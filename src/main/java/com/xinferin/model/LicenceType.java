/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.model;

public enum LicenceType {
    LIMITED(1), UNLIMITED(2), TRIAL(3), TIME_BASED(4);
    
    private int ltype;
    
    private LicenceType(int ltype){
    	this.ltype = ltype;
    }
    
    public int getLicenceType(){
    	return ltype;
    }
};
