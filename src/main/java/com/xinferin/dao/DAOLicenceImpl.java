/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.xinferin.model.Licence;

public class DAOLicenceImpl implements DAOLicence {
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public DAOLicenceImpl(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int add(Licence licence) {
        String sql = "INSERT INTO licence (generated_key, product_id, expiry_date, comments)"
                + " VALUES (:generated_key, :product_id, :expiry_date, :comments)";
    
	    SqlParameterSource sqlParameters = new BeanPropertySqlParameterSource(licence);
		KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(sql, sqlParameters, keyHolder);
	    
	    return keyHolder.getKey().intValue();
	}
}
