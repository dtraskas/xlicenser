/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.dao;

import java.util.List;

import java.sql.ResultSet;
import javax.sql.DataSource;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.xinferin.model.Customer;

public class DAOCustomerImpl implements DAOCustomer {
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public DAOCustomerImpl(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int add(Customer customer){
        String sql = "INSERT INTO customer (fname, lname, company, street, city, state, country, postcode, telephone, email, comments)"
                    + " VALUES (:fname, :lname, :company, :street, :city, :state, :country, :postcode, :telephone, :email, :comments)";
        
        SqlParameterSource sqlParameters = new BeanPropertySqlParameterSource(customer);
		KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, sqlParameters, keyHolder);
        
        return keyHolder.getKey().intValue();
	}
	
	@Override
	public void edit(Customer customer) {
		if (customer.getId() > 0) {
	        String sql = "UPDATE customer SET fname=:fname, lname=:lname, company=:company, street=:street, "
	        			+ "city=:city, state=:state, country=:country, postcode=:postcode, "
	        			+ "telephone=:telephone, email=:email, comments=:comments WHERE id=:id";
	        
	        SqlParameterSource sqlParameters = new BeanPropertySqlParameterSource(customer);
			jdbcTemplate.update(sql, sqlParameters);	        
	    }
	}

	@Override
	public void delete(int customerId) {
		String sql = "DELETE FROM customer WHERE id=:id";
		SqlParameterSource args = new MapSqlParameterSource().addValue("id",customerId);
	    jdbcTemplate.update(sql, args);
	}

	@Override
	public Customer get(int customerId) {
		String sql = "SELECT * FROM customer WHERE id=" + customerId;
	    return jdbcTemplate.query(sql, new ResultSetExtractor<Customer>() {
	 
	        @Override
	        public Customer extractData(ResultSet rs) throws SQLException, DataAccessException {
	            
	        	if (rs.next()) {
	            	Customer customer = new Customer();
	            	customer.setId(rs.getInt("id"));
	            	customer.setFname(rs.getString("fname"));
	            	customer.setLname(rs.getString("lname"));
	            	customer.setCompany(rs.getString("company"));	            	
	            	customer.setStreet(rs.getString("street"));
	            	customer.setCity(rs.getString("city"));
	            	customer.setState(rs.getString("state"));
	            	customer.setCountry(rs.getString("country"));
	            	customer.setPostcode(rs.getString("postcode"));	            	
	            	customer.setTelephone(rs.getString("telephone"));	                
	            	customer.setEmail(rs.getString("email"));
	            	customer.setComments(rs.getString("comments"));
	            	customer.setDateAdded(rs.getDate("date_added"));
	            	
	            	return customer;
	            }
	            return null;
	        }
	 
	    });
	}

	@Override
	public List<Customer> list() {
		String sql = "SELECT * FROM customer";
	    List<Customer> list = jdbcTemplate.query(sql, new RowMapper<Customer>() {
	        @Override
	        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	Customer aCustomer= new Customer();
	 
	        	aCustomer.setId(rs.getInt("id"));
	        	aCustomer.setFname(rs.getString("fname"));
	        	aCustomer.setLname(rs.getString("lname"));
	        	aCustomer.setCompany(rs.getString("company"));	            	
	        	aCustomer.setStreet(rs.getString("street"));
	        	aCustomer.setCity(rs.getString("city"));
	        	aCustomer.setState(rs.getString("state"));
	        	aCustomer.setCountry(rs.getString("country"));
	        	aCustomer.setPostcode(rs.getString("postcode"));	            	
	        	aCustomer.setTelephone(rs.getString("telephone"));	                
	        	aCustomer.setEmail(rs.getString("email"));
	        	aCustomer.setComments(rs.getString("comments"));
	        	aCustomer.setDateAdded(rs.getDate("date_added"));
	        	
	            return aCustomer;
	        }
	    });
	    return list;
	}
}
