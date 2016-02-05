package com.bnaqica.person.dao;

import java.util.List;

import com.bnaqica.person.model.Person;

public interface PersonDAO {
	void addPerson(Person person);
	
	void editPerson(Person person);
	
	void delete(int id);
	
	Person retrievePerson(int id);
	
	List<Person> retrivePeople();
	
}
