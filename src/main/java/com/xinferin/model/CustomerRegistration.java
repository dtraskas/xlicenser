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

import com.xinferin.Utils;

public class CustomerRegistration {
	
	private Customer customer;
	private String licenceType;
	private String product;
	private String provider;
	private double price;
	private int daysToExpire;
	
	public CustomerRegistration(){
		setCustomer(null);
		setLicenceType("");
		setProduct("");
		setProvider("");
		setPrice(0);
		setDaysToExpire(0);
	}
	
	public CustomerRegistration(Customer customer, String licenceType, String product, 
								String provider, double price, int daysToExpire){
		setCustomer(customer);
		setLicenceType(licenceType);
		setProduct(product);
		setProvider(provider);
		setPrice(price);
		setDaysToExpire(daysToExpire);
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public LicenceType getLicenceType() {
		return LicenceType.valueOf(licenceType);
	}
	
	public void setLicenceType(String licenceType) {
		this.licenceType = licenceType;
	}
	
	public int getDaysToExpire() {
		return daysToExpire;
	}
	
	public void setDaysToExpire(int daysToExpire) {
		this.daysToExpire = daysToExpire;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getExpiryDate() {		
		Calendar currentDate = Calendar.getInstance();
		return Utils.addDays(currentDate.getTime(), this.daysToExpire);
	}	
}
