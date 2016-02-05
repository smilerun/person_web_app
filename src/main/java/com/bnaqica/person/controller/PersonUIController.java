package com.bnaqica.person.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bnaqica.person.dao.PersonDAO;
import com.bnaqica.person.model.Person;

@Controller
@RequestMapping("ui")
public class PersonUIController {
	private final Logger logger = LoggerFactory.getLogger(PersonUIController.class);

	@Autowired
	PersonDAO personDAO;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		binder.registerCustomEditor(Date.class, "dateOfBirth",
				new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("/")
	public ModelAndView getMainPage() {
		logger.info("In main page to display all people.....");
		 List<Person> people = personDAO.retrivePeople();
		return new ModelAndView("mainPage", "people", people);
	}

	@RequestMapping("/main")
	public ModelAndView getMainPageRedirect() {
		List<Person> people = personDAO.retrivePeople();
		return new ModelAndView("mainPage", "people", people);
	}

	@RequestMapping("/edit")
	public ModelAndView editUserInfo(@RequestParam int personId, @ModelAttribute Person person) {
		Person personEdited = personDAO.retrievePerson(personId);
		return new ModelAndView("editUser", "personEdited", personEdited);
	}

	@RequestMapping(value="/update", method = RequestMethod.POST)
	public ModelAndView updateUserInfo(@ModelAttribute("person") Person person, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("addUserForm");
		}
		
		logger.info("Person edited is: " + person);
		personDAO.editPerson(person);
		return new ModelAndView("redirect:/ui/main");
	}

	@RequestMapping("/delete")
	
	public ModelAndView deleteUser(@RequestParam int personId) {
		logger.info("Deleting person with id " + personId);
		
		personDAO.delete(personId);
		return new ModelAndView("redirect:/ui/main");
	}

	@RequestMapping("/addUser")
	public ModelAndView getAddUserForm(@ModelAttribute Person person) {
		return new ModelAndView("addUserForm");
	}

	@RequestMapping(value="/processAddUser", method = RequestMethod.POST)
	public ModelAndView ProcessAddUser(@ModelAttribute("person") Person person, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("addUserForm");
		}
		
		personDAO.addPerson(person);
		return new ModelAndView("redirect:/ui/main");
	}

}
