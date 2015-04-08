/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.xinferin.config.MvcConfiguration;
import com.xinferin.dao.DAOCustomer;
import com.xinferin.model.Customer;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private MvcConfiguration config = new MvcConfiguration();
	
	@Autowired	
	private DAOCustomer daoCustomer = config.getDAOCustomer();
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void addCustomer(@RequestBody Customer customer){
		daoCustomer.add(customer);		
	}
	
	@RequestMapping(value = "/{customerId}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseStatus(HttpStatus.OK)	
	public void editCustomer(@PathVariable int customerId, @RequestBody Customer customer){
		customer.setId(customerId);
		daoCustomer.edit(customer);		
	}	
	
	@RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)	
	public void deleteCustomer(@PathVariable int customerId){
		daoCustomer.delete(customerId);				
	} 
	
	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)	
	public Customer getCustomer(@PathVariable int customerId){
		return daoCustomer.get(customerId);		
	} 
		
	@RequestMapping(method = RequestMethod.GET)	
	public List<Customer> customers() {				
		return daoCustomer.list();
	}
}
