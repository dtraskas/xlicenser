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
	private String licenceParams;
	
	public Product(){
		this.setId(-1);
		this.setName("");
		this.setLicenceParams("");
	}
	
	public Product(int id, String name){
		this.setId(id);
		this.setName(name);
		this.setLicenceParams("");
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

	public String getLicenceParams() {
		return licenceParams;
	}

	public void setLicenceParams(String licenceParams) {
		this.licenceParams = licenceParams;
	}
}
