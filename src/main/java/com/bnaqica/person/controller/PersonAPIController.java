package com.bnaqica.person.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bnaqica.person.dao.ApiLogRequestDAO;
import com.bnaqica.person.dao.PersonDAO;
import com.bnaqica.person.model.Person;
import com.bnaqica.person.util.IoUtil;
import com.bnaqica.person.util.time.TimeSource;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static com.bnaqica.person.util.ObjectMapperProvider.getObjectMapper;
import static java.text.MessageFormat.format;

@Controller
public class PersonAPIController {
	private final Logger logger = LoggerFactory
			.getLogger(PersonAPIController.class);
	private final ObjectMapper jsonMapper = getObjectMapper();

	@Autowired
	private PersonDAO personDAO;
	@Autowired
	private TimeSource timeSource;
	@Autowired
	private ApiLogRequestDAO apiRequestLogDAO;

	@RequestMapping(value = "/persons", method = POST, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody Object postPeople(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String jsonContent = IoUtil.readFully(new StringBuilder(),
				request.getReader()).toString();
		logger.info("person posted.  json={}", jsonContent);

		apiRequestLogDAO.logApiRequest(jsonContent, request.getRequestURI(), request.getRemoteAddr(),
				request.getRemoteHost(), timeSource.now());

		Person person;
		try {
			person = jsonMapper.readValue(jsonContent, Person.class);
		} catch (IOException e) {
			logger.error("failed recognizing person json.  json={}",
					jsonContent, e);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return "Json Failed";
		}

		personDAO.addPerson(person);
		logger.info("person saved.  person={}", person);

		response.setStatus(HttpServletResponse.SC_CREATED);
		return "Open/Created";
	}
	
	@RequestMapping(value = "/person/{personId}", produces = APPLICATION_JSON_VALUE)
	public @ResponseBody Object getPerson(@PathVariable int personId, HttpServletResponse response) throws IOException  {
        Person person = personDAO.retrievePerson(personId);

        if (person.equals(null)) {
            logger.warn("request for person.  personId={}", personId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return format("Order with id {0} could not be found.", personId);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            return jsonMapper.writeValueAsString(person);
        }
	}
}
