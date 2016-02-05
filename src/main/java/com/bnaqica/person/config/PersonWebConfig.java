package com.bnaqica.person.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.bnaqica.person.dao.ApiLogRequestDAO;
import com.bnaqica.person.dao.MySqlApiLogRequest;
import com.bnaqica.person.dao.MySqlPersonDAO;
import com.bnaqica.person.dao.PersonDAO;
import com.bnaqica.person.util.time.SystemTimeSource;
import com.bnaqica.person.util.time.TimeSource;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.bnaqica.person.controller" })
public class PersonWebConfig extends WebMvcConfigurerAdapter{
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/person");
        dataSource.setUsername("personDev");
        dataSource.setPassword("abc123");
        
         
        return dataSource;
    }
    
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean PersonDAO getPersonDAO() {
		return new MySqlPersonDAO(getDataSource());
	}
	
	@Bean ApiLogRequestDAO getApiRequestLogDAO() {
		return new MySqlApiLogRequest(getDataSource());
	}
	
	@Bean TimeSource getTimeSource() {
		return new SystemTimeSource();
	}
	
}
