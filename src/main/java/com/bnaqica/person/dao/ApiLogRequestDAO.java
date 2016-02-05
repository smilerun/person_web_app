package com.bnaqica.person.dao;

import java.time.LocalDateTime;

public interface ApiLogRequestDAO {
	void logApiRequest(String jsonContent, String requestURI, String requestRemoteAddress, String requestRemoteHost, LocalDateTime timestamp);
}
