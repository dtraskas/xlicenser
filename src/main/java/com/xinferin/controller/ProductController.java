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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.xinferin.config.MvcConfiguration;
import com.xinferin.dao.DAOProduct;
import com.xinferin.model.Product;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private MvcConfiguration config = new MvcConfiguration();
	
	@Autowired	
	private DAOProduct daoProduct = config.getDAOProduct();
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void addProduct(@RequestBody Product product){
		daoProduct.add(product);
	}
	
	@RequestMapping(value = "/{productId}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseStatus(HttpStatus.OK)	
	public void editProduct(@PathVariable int productId, @RequestBody Product product){
		product.setId(productId);
		daoProduct.edit(product);		
	}
	
	@RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)	
	public void deleteProduct(@PathVariable int productId){
		daoProduct.delete(productId);				
	} 
	
	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)	
	public Product getProduct(@PathVariable int productId){
		Product product = daoProduct.get(productId);
		daoProduct.deleteTemp(productId);
		
		return product;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Product> products() {		
		DAOProduct daoProduct = config.getDAOProduct();
		return daoProduct.list();
	}
}
