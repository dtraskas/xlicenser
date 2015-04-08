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

import com.xinferin.model.Provider;

public class DAOProviderImpl implements DAOProvider {
	
	private JdbcTemplate jdbcTemplate;
	 
	public DAOProviderImpl(DataSource dataSource) {
	    jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Provider> list() {
		String sql = "SELECT * FROM provider";
	    List<Provider> list = jdbcTemplate.query(sql, new RowMapper<Provider>() {
	        @Override
	        public Provider mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	Provider aProvider = new Provider();
	 
	        	aProvider.setId(rs.getInt("id"));
	        	aProvider.setName(rs.getString("name"));
	            
	            return aProvider;
	        }
	    });
	    return list;
	}
}