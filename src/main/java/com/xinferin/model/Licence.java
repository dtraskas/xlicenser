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
	private String generated_key;
	private int product_id;
	private java.sql.Date expiry_date;
	private String comments;
	
	public Licence(){
		setGenerated_key("");
		setProduct_id(-1);
		setExpiry_date(Calendar.getInstance().getTime());
		setComments("");
	}
	
	public String getGenerated_key() {
		return generated_key;
	}
	
	public void setGenerated_key(String generated_key) {
		this.generated_key = generated_key;
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