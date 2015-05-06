/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.model;

public class LicenceReply {
	private String key_info;
	private String generated_key;
	
	public LicenceReply(){
		setKey_info("");
		setGenerated_key("");
	}

	public String getKey_info() {
		return key_info;
	}

	public void setKey_info(String key_info) {
		this.key_info = key_info;
	}

	public String getGenerated_key() {
		return generated_key;
	}

	public void setGenerated_key(String generated_key) {
		this.generated_key = generated_key;
	}
}
