package com.vatManager.vatManager.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

	@Bean
	public DataSource dataSource() throws IOException{
		String dbPath = DbInitializer.initializeDb("TDS-ClientManger");
		String url = "jdbc:sqlite:" + dbPath;
		
		org.sqlite.SQLiteDataSource ds = new org.sqlite.SQLiteDataSource();
		ds.setUrl(url);
		return ds;
	}
	
	
}
