package com.bnaqica.person.dao;

import static com.bnaqica.person.model.PersonBuilder.personBuilder;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bnaqica.person.model.Person;

@Repository
public class MySqlPersonDAO implements PersonDAO {
	private final Logger logger = LoggerFactory.getLogger(MySqlPersonDAO.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final JdbcOperations jdbcOperations;

	public MySqlPersonDAO(DataSource dataSource) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		jdbcOperations = namedParameterJdbcTemplate.getJdbcOperations();
	}
	
	@Override
	public void addPerson(Person person) {
		logger.info("Adding person: " + person);
		jdbcOperations.update("INSERT INTO person (first_name, last_name, date_of_birth, phone_number, salary, state, email)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)",
				person.getFirstName(), 
				person.getLastName(),
				person.getDateOfBirth(), 
				person.getPhoneNumber(),
				person.getSalary(), 
				person.getState(),
				person.getEmail());
	}

	@Override
	public void editPerson(Person person) {
		logger.info("Editing person: " + person);
		jdbcOperations.update("UPDATE person SET first_name = ?, last_name = ?, date_of_birth = ?,"
				+ " phone_number = ?, salary = ?, state = ?, email = ? WHERE id = ?",
				person.getFirstName(), 
				person.getLastName(),
				person.getDateOfBirth(), 
				person.getPhoneNumber(),
				person.getSalary(), 
				person.getState(),
				person.getEmail(), 
				person.getPersonId());
	}

	@Override
	public void delete(int personId) {
		jdbcOperations.update("DELETE from person WHERE id = ?", personId);
	}

	@Override
	public Person retrievePerson(int personId) {
		String sql = "SELECT id, first_name, last_name, date_of_birth, phone_number, salary, state, email  FROM person WHERE id=" + personId;
		return jdbcOperations.queryForObject(sql, (rs, rowNum) -> {
					return personBuilder().personId(rs.getInt(1))
							.firstName(rs.getString(2))
							.lastName(rs.getString(3))
							.dateOfBirth(rs.getDate(4))
							.phoneNumber(rs.getString(5))
							.salary(rs.getDouble(6)).state(rs.getString(7))
							.email(rs.getString(8)).build();
				});
	}

	@Override
	public List<Person> retrivePeople() {
		String sql = "SELECT id, first_name, last_name, date_of_birth, phone_number, salary, state, email FROM person";
		return jdbcOperations.query(sql, (rs, rowCount) -> {
					return personBuilder().personId(rs.getInt(1))
							.firstName(rs.getString(2))
							.lastName(rs.getString(3))
							.dateOfBirth(rs.getDate(4))
							.phoneNumber(rs.getString(5))
							.salary(rs.getDouble(6)).state(rs.getString(7))
							.email(rs.getString(8)).build();
				});
	}

}
