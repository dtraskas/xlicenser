/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.dao;

import java.sql.ResultSet;
import java.util.List;
import javax.sql.DataSource;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.xinferin.model.Product;

public class DAOProductImpl implements DAOProduct {
	
	private JdbcTemplate jdbcTemplate;
	
	public DAOProductImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Product> list() {
		String sql = "SELECT * FROM product";
	    List<Product> list = jdbcTemplate.query(sql, new RowMapper<Product>() {
	        @Override
	        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	Product aProduct = new Product();
	 
	        	aProduct.setId(rs.getInt("id"));
	        	aProduct.setName(rs.getString("name"));
	        	aProduct.setLicenceParams(rs.getString("licence_params"));	            
	        	
	            return aProduct;
	        }
	    });
	    return list;
	}
}
