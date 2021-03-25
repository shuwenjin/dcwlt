package com.dcits.dcwlt.pay.batch.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

	@Bean
	@ConfigurationProperties("spring.datasource.dynamic.datasource.master")
	public HikariConfig hikariConfig() {
		HikariConfig config = new HikariConfig();
		return config;
	}

	@Bean
	@Primary
	@Autowired
	public DataSource dataSource(HikariConfig hikariConfig) {
		return new HikariDataSource(hikariConfig);
	}
}
