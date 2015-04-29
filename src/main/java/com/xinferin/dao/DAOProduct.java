/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.dao;

import java.util.List;
import com.xinferin.model.Product;

public interface DAOProduct {
	
	public void add(Product product);
	
	public void edit(Product product);
	
	public void delete(int productId);
	
	public void deleteTemp(int productId);
	
	public Product get(int productId);
	
	public Product get(String productName);
	
	public List<Product> list();
}
