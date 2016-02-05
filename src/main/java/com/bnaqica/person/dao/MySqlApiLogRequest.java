package com.bnaqica.person.dao;

import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static java.sql.Timestamp.valueOf;

@Repository
public class MySqlApiLogRequest implements ApiLogRequestDAO {
	private JdbcTemplate jdbcTemplate;
	
	public MySqlApiLogRequest(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void logApiRequest(String jsonContent, String requestURI, String requestRemoteAddress, String requestRemoteHost, LocalDateTime timestamp) {
		jdbcTemplate.update("INSERT INTO api_request_log(payload, server_local_address, request_uri, request_address, request_hostname, timestamp)"
				          + " VALUES (?, ?, ?, ?, ?, ?)", jsonContent, "local_server", requestURI, requestRemoteAddress, requestRemoteHost, valueOf(timestamp));
	}

}
