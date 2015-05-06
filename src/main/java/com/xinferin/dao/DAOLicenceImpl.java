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

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.xinferin.model.KeyRequest;
import com.xinferin.model.Licence;
import com.xinferin.model.LicenceReply;

public class DAOLicenceImpl implements DAOLicence {
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public DAOLicenceImpl(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int add(Licence licence) {
        String sql = "INSERT INTO licence (product_id, issue_date)"
                + " VALUES (:product_id, NOW())";
    
	    SqlParameterSource sqlParameters = new BeanPropertySqlParameterSource(licence);
		KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(sql, sqlParameters, keyHolder);
	    
	    return keyHolder.getKey().intValue();
	}
	
	@Override
	public void update(Licence licence) {
		String sql = "UPDATE licence SET key_info = :key_info, generated_key = :generated_key, "
					 + "customer_key = :customer_key, expiry_date = :expiry_date, comments = :comments, can_revoke = 0 WHERE id = :id";
    
	    SqlParameterSource sqlParameters = new BeanPropertySqlParameterSource(licence);		
	    jdbcTemplate.update(sql, sqlParameters);
	}
	
	@Override
	public String checkRevoke(int licenceId) {
		String sql = "SELECT can_revoke FROM xlicenser.licence WHERE id = :id";
		
		MapSqlParameterSource args = new MapSqlParameterSource();
		args = new MapSqlParameterSource();
		args.addValue("id", licenceId);
		int can_revoke = jdbcTemplate.queryForObject(sql, args, Integer.class);
		
		if (can_revoke == 1)
		{
			return "REVOKE";
		} else {
			return "OK";
		}
	}
	
	@Override
	public LicenceReply get(KeyRequest keyRequest){
		
		LicenceReply lr = null;
		String sql = "SELECT licence_id FROM xlicenser.licence_registration" 
					 + " WHERE registration_id = (SELECT id FROM xlicenser.registration" 
					 + "  				          WHERE customer_id = (SELECT id FROM xlicenser.customer WHERE email = :email)"
					 + ")";
		MapSqlParameterSource args = new MapSqlParameterSource();
		args.addValue("email", keyRequest.getEmail());
		int licence_id_one = jdbcTemplate.queryForObject(sql, args, Integer.class);
		
		sql = "SELECT id FROM xlicenser.licence WHERE customer_key = :key";
		args = new MapSqlParameterSource();
		args.addValue("key", keyRequest.getKey());
		int licence_id_two = jdbcTemplate.queryForObject(sql, args, Integer.class);
		
		if (licence_id_one == licence_id_two){
			sql = "SELECT key_info, generated_key FROM xlicenser.licence WHERE customer_key = :key";
			args = new MapSqlParameterSource();
			args.addValue("key", keyRequest.getKey());
			
			lr = jdbcTemplate.query(sql, args, new ResultSetExtractor<LicenceReply>() {
				 
		        @Override
		        public LicenceReply extractData(ResultSet rs) throws SQLException, DataAccessException {
		            
		        	if (rs.next()) {
		        		LicenceReply lr = new LicenceReply();
		        		lr.setKey_info(rs.getString("key_info"));
		        		lr.setGenerated_key(rs.getString("generated_key"));
		        		
		            	return lr;
		            }
		            return null;
		        }
		    });
		}		
		return lr;
	}	
}
