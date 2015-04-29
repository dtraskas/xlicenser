/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.dao;

import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xinferin.licensing.LicenceGenerator;
import com.xinferin.model.CustomerRegistration;
import com.xinferin.model.KeyRequest;
import com.xinferin.model.Licence;
import com.xinferin.model.LicenceType;
import com.xinferin.model.Product;
import com.xinferin.model.Provider;

public class DAOCustomerRegistrationImpl implements DAOCustomerRegistration {

	private NamedParameterJdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	
	public DAOCustomerRegistrationImpl(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void register(CustomerRegistration creg) throws Exception {
		
		DAOProductImpl daoProductImpl = new DAOProductImpl(dataSource);
		Product product = daoProductImpl.get(creg.getProduct());
		if (product == null) throw new ProductNotFoundException(creg.getProduct());
		
		DAOProviderImpl daoProviderImpl = new DAOProviderImpl(dataSource);
		Provider provider = daoProviderImpl.get(creg.getProvider());
		if (provider == null) throw new ProviderNotFoundException(creg.getProvider());
		
		// add a new customer
		DAOCustomerImpl daoCustomerImpl = new DAOCustomerImpl(dataSource);
		int customerId = daoCustomerImpl.add(creg.getCustomer());		
		if (customerId < 0) throw new CustomerNotCreatedException();
		
		// register the new customer
		int registrationId = addRegistration(customerId, product.getId(), provider.getId(), creg.getLicenceType(), creg.getPrice());						
		if (registrationId < 0) throw new RegistrationNotCreatedException();
		
		// use the LicenceGenerator to create a new licence key using PRODUCT | CUSTOMER FULLNAME | CUSTOMER EMAIL | LICENCE TYPE | EXPIRY DATE   
		String key_info = creg.getProduct() + "|" + 
						  creg.getCustomer().getFname() + ", " + creg.getCustomer().getLname() + "|" + 
						  creg.getCustomer().getEmail() + "|" + 
						  creg.getLicenceType() + "|" + 
						  creg.getDaysToExpire();
		
		LicenceGenerator lg = new LicenceGenerator(product.getLicence_params());
		byte[] generatedKey = lg.signData(key_info.getBytes());
		String signedB64 = Base64.encodeBase64String(generatedKey);
		
		// add a new licence with a generated key.
		Licence licence = new Licence();
		licence.setKey_info(key_info);
		licence.setGenerated_key(signedB64);
		licence.setCustomer_key(UUID.randomUUID().toString());
		licence.setProduct_id(product.getId());
		licence.setExpiry_date(creg.getExpiryDate());
		
		DAOLicenceImpl daoLicenceImpl = new DAOLicenceImpl(dataSource);
		int licenceId = daoLicenceImpl.add(licence);		
		
		// bind the registration with the licence.
		bindRegistrationAndLicence(registrationId, licenceId);	
	}
	
	@Override
	public String getLicence(KeyRequest keyRequest){
		
		String generatedKey = "";
		try {
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
				sql = "SELECT generated_key FROM xlicenser.licence WHERE customer_key = :key";
				args = new MapSqlParameterSource();
				args.addValue("key", keyRequest.getKey());
				
				generatedKey = jdbcTemplate.queryForObject(sql, args, String.class);
			}
		} catch (Exception ex) {
			return "";
		}
		return generatedKey;
	}
	
	private int addRegistration(int customerId, int productId, int providerId, LicenceType licenceType, double price){
		String sql = "INSERT INTO registration (date, customer_id, product_id, provider_id, licence_type, price)"
                + " VALUES (NOW(), :customer_id, :product_id, :provider_id, :licence_type, :price)";
		
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
