/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.xinferin.model.Activation;

public class DAOActivationImpl implements DAOActivation {
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public DAOActivationImpl(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int add(Activation activation) {
		String sql = "INSERT INTO activation (licence_id, date, machine_code, ipaddress, success)"
                + " VALUES (:licence_id, NOW(), :machine_code, :ipaddress, :success)";
    
	    SqlParameterSource sqlParameters = new BeanPropertySqlParameterSource(activation);
		KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(sql, sqlParameters, keyHolder);
	    
	    return keyHolder.getKey().intValue();
	}

	@Override
	public List<Activation> list() {
		String sql = "SELECT * FROM activation";
	    List<Activation> list = jdbcTemplate.query(sql, new RowMapper<Activation>() {
	        @Override
	        public Activation mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	Activation aActivation= new Activation();
	 
	        	aActivation.setLicence_id(rs.getInt("licence_id"));
	        	aActivation.setDate(rs.getDate("date"));
	        	aActivation.setMachine_code(rs.getString("machine_code"));
	        	aActivation.setIpaddress(rs.getString("ipaddress"));
	        	aActivation.setSuccess(rs.getInt("success"));
	        	
	            return aActivation;
	        }
	    });
	    return list;
	}
}
