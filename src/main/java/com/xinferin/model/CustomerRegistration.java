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
	//private LicenceType licenceType;
	private String licenceType;
	private int productId;
	private int providerId;
	private double price;
	private int daysToExpire;
	
	public CustomerRegistration(){
		setCustomer(null);
		setLicenceType("");
		setProductId(-1);
		setProviderId(-1);
		setPrice(0);
		setDaysToExpire(0);
	}
	
	public CustomerRegistration(Customer customer, String licenceType, int productId, 
								int providerId, double price, int daysToExpire){
		setCustomer(customer);
		//setLicenceType(LicenceType.valueOf(licenceType));
		setLicenceType(licenceType);
		setProductId(productId);
		setProviderId(providerId);
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

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
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
