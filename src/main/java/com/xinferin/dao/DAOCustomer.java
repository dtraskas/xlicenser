/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.dao;

import java.util.List;

import com.xinferin.model.Customer;

public interface DAOCustomer {
	
	public int add(Customer customer);
	
	public void edit(Customer customer);
	
	public void delete(int customerId);
	
	public Customer get(int customerId);
	
	public List<Customer> list();
}
