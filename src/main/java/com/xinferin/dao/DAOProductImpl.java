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

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xinferin.licensing.LicenceGenerator;
import com.xinferin.model.Product;

public class DAOProductImpl implements DAOProduct {
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public DAOProductImpl(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void add(Product product){
		
		// generate the private and public keys and then store them in the database.
 		LicenceGenerator lg = new LicenceGenerator(null);
 		product.setLicence_params(lg.getEncodedPrivateKey());
 		product.setTemp(lg.getEncodedToXMLPublicKey());
 		
		String sql = "INSERT INTO product (id, name, licence_params, temp)"
                    + " VALUES (:id, :name, :licence_params, :temp)";
        
        SqlParameterSource sqlParameters = new BeanPropertySqlParameterSource(product);
		jdbcTemplate.update(sql, sqlParameters);
	}
	
	@Override
	public void edit(Product product) {
		if (product.getId() > 0) {
	        String sql = "UPDATE product SET name=:fname WHERE id=:id";
	        
	        SqlParameterSource sqlParameters = new BeanPropertySqlParameterSource(product);
			jdbcTemplate.update(sql, sqlParameters);	        
	    }		
	}

	@Override
	public void delete(int productId) {
		String sql = "DELETE FROM product WHERE id=:id";
		SqlParameterSource args = new MapSqlParameterSource().addValue("id", productId);
	    jdbcTemplate.update(sql, args);
	}

	@Override
	public Product get(int productId) {
		
		// retrieve the details of this product including temp if it exists
		String sql = "SELECT * FROM product WHERE id=" + productId;
	    return jdbcTemplate.query(sql, new ResultSetExtractor<Product>() {
	 
	        @Override
	        public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
	            
	        	if (rs.next()) {
	        		Product product = new Product();
	        		product.setId(rs.getInt("id"));
	        		product.setName(rs.getString("name"));
	        		product.setLicence_params(rs.getBytes("licence_params"));	            	
	            	
	        		if (rs.getString("temp") != null){
	        			product.setTemp(rs.getString("temp"));
	        		}
	            	return product;
	            }
	            return null;
	        }
	    });
	}
	
	@Override
	public Product get(String product) {
		String sql = "SELECT * FROM product WHERE name = '" + product + "'";
	    return jdbcTemplate.query(sql, new ResultSetExtractor<Product>() {
	 
	        @Override
	        public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
	            
	        	if (rs.next()) {
	        		Product product = new Product();
	        		product.setId(rs.getInt("id"));
	        		product.setName(rs.getString("name"));
	        		product.setLicence_params(rs.getBytes("licence_params"));	            	
	            	return product;
	            }
	            return null;
	        }
	    });
	}	
	
	@Override
	public void deleteTemp(int productId){		
		// remove temp since it is read-only for only one time.
	    String sql = "UPDATE product SET temp = NULL WHERE id = :id";
	    SqlParameterSource args = new MapSqlParameterSource().addValue("id", productId);
	    jdbcTemplate.update(sql, args);
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
	        	aProduct.setLicence_params(rs.getBytes("licence_params"));	            	
            	
	            return aProduct;
	        }
	    });
	    return list;
	}
}
