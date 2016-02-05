package com.bnaqica.person.model;

import java.util.Date;

public class PersonBuilder {
	private int personId;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String phoneNumber;
	private Double salary;
	private String state;
	private String email;

	public PersonBuilder personId(int personId) {
		this.personId = personId;
		return this;
	}

	public PersonBuilder firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public PersonBuilder lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public PersonBuilder dateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public PersonBuilder phoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public PersonBuilder salary(Double salary) {
		this.salary = salary;
		return this;
	}

	public PersonBuilder state(String state) {
		this.state = state;
		return this;
	}

	public PersonBuilder email(String email) {
		this.email = email;
		return this;
	}

	public Person build() {
		return new Person(personId, firstName, lastName, dateOfBirth,
				phoneNumber, salary, state, email);
	}

	public static PersonBuilder personBuilder() {
		return new PersonBuilder();
	}

}
