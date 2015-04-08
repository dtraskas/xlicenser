/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.xinferin.model.CustomerRegistration;
import com.xinferin.model.Licence;
import com.xinferin.model.LicenceType;

public class DAOCustomerRegistrationImpl implements DAOCustomerRegistration {

	private NamedParameterJdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	
	public DAOCustomerRegistrationImpl(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public void register(CustomerRegistration creg) {
		
		// add a new customer
		DAOCustomerImpl daoCustomerImpl = new DAOCustomerImpl(dataSource);
		int customerId = daoCustomerImpl.add(creg.getCustomer());
		
		// add a new licence with a generated key.
		Licence licence = new Licence();
		licence.setGenerated_key("test");
		licence.setProduct_id(creg.getProductId());
		licence.setExpiry_date(creg.getExpiryDate());
		
		DAOLicenceImpl daoLicenceImpl = new DAOLicenceImpl(dataSource);
		int licence_id = daoLicenceImpl.add(licence);
		
		// register the new customer
		int registration_id = addRegistration(customerId, creg.getProductId(), creg.getProviderId(), creg.getLicenceType(), creg.getPrice());
		
		// bind the registration with the licence.
		bindRegistrationAndLicence(registration_id, licence_id);
	}
	
	private int addRegistration(int customerId, int productId, int providerId, LicenceType licenceType, double price){
		String sql = "INSERT INTO registration (customer_id, product_id, provider_id, licence_type, price)"
                + " VALUES (:customer_id, :product_id, :provider_id, :licence_type, :price)";
		
		MapSqlParameterSource args = new MapSqlParameterSource();
		args.addValue("customer_id", customerId);
		args.addValue("product_id", productId);
		args.addValue("provider_id", providerId);
		args.addValue("licence_type", licenceType.getLicenceType());		
		args.addValue("price", price);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(sql, args, keyHolder);
		
		return keyHolder.getKey().intValue();
	}
	
	private void bindRegistrationAndLicence(int registration_id, int licence_id) {
		String sql = "INSERT INTO licence_registration (registration_id, licence_id)"
                + " VALUES (:registration_id, :licence_id)";
		
		MapSqlParameterSource args = new MapSqlParameterSource();
		args.addValue("registration_id", registration_id);
		args.addValue("licence_id", licence_id);
		
		jdbcTemplate.update(sql, args);
	}
}
