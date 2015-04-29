/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.model;

public class Activation {
	
	private int licence_id;
	private java.sql.Date date;
	private String machine_code;
	private String ipaddress;
	private int success;
	
	public Activation(){
		setLicence_id(-1);
		setDate(null);
		setMachine_code("");
		setIpaddress("");
		setSuccess(0);
	}
	
	public int getLicence_id() {
		return licence_id;
	}
	
	public void setLicence_id(int licence_id) {
		this.licence_id = licence_id;
	}
	
	public java.sql.Date getDate() {
		return date;
	}
	
	public void setDate(java.sql.Date date) {
		this.date = date;
	}
	
	public String getMachine_code() {
		return machine_code;
	}
	
	public void setMachine_code(String machine_code) {
		this.machine_code = machine_code;
	}
	
	public String getIpaddress() {
		return ipaddress;
	}
	
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
	public int getSuccess() {
		return success;
	}
	
	public void setSuccess(int success) {
		this.success = success;
	}
}
