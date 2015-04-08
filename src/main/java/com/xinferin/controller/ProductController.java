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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xinferin.config.MvcConfiguration;
import com.xinferin.dao.DAOProduct;
import com.xinferin.model.Product;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private MvcConfiguration config = new MvcConfiguration();
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Product> products() {		
		DAOProduct daoProduct = config.getDAOProduct();
		return daoProduct.list();
	}
}
