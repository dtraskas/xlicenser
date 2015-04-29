/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.model;

import java.util.Calendar;
import java.util.Date;

public class Licence {
	private String key_info;
	private String generated_key;
	private String customer_key;
	private int product_id;
	private java.sql.Date expiry_date;
	private String comments;
	
	public Licence(){
		setKey_info("");
		setGenerated_key("");
		setCustomer_key("");
		setProduct_id(-1);
		setExpiry_date(Calendar.getInstance().getTime());
		setComments("");
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
	
	public String getCustomer_key() {
		return customer_key;
	}

	public void setCustomer_key(String customer_key) {
		this.customer_key = customer_key;
	}
	
	public int getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	
	public java.sql.Date getExpiry_date() {
		return expiry_date;		
	}
	
	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = new java.sql.Date(expiry_date.getTime());
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
}