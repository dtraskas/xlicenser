/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.xinferin.dao.*;

@Configuration
@ComponentScan(basePackages="net.codejava.spring")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter{

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/xlicenser");
        dataSource.setUsername("root");
        dataSource.setPassword("x1nfer1n");
         
        return dataSource;
    }
     
    @Bean
    public DAOProvider getDAOProvider() {
        return new DAOProviderImpl(getDataSource());
    }

    @Bean
	public DAOProduct getDAOProduct() {
		return new DAOProductImpl(getDataSource());
	}
    
    @Bean
    public DAOCustomer getDAOCustomer() {
    	return new DAOCustomerImpl(getDataSource());
    }
    
    @Bean
    public DAOCustomerRegistration getDAOCustomerRegistration() {
    	return new DAOCustomerRegistrationImpl(getDataSource());
    }
    
    @Bean
	public DAOActivation getDAOActivation() {
		return new DAOActivationImpl(getDataSource());
	}
    
    @Bean
	public DAOLicence getDAOLicence() {
		return new DAOLicenceImpl(getDataSource());
	}
}