/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.model;

public class Product {
	
	private int id;
	private String name;
	private byte[] licence_params;
	private String temp;
	
	public Product(){
		this.setId(-1);
		this.setName("");
		this.setLicence_params(null);
		this.setTemp("");
	}
	
	public Product(int id, String name){
		this.setId(id);
		this.setName(name);
		this.setLicence_params(null);
		this.setTemp("");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getLicence_params() {
		return licence_params;
	}

	public void setLicence_params(byte[] licenceParams) {
		this.licence_params = licenceParams;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
}
