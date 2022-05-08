package com.health.foo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.foo.models.Person;
import com.health.foo.repositories.PersonRepository;

@RestController
@Validated
@RequestMapping(path = "/api")
public class FooHealthController {

	@Autowired
	PersonRepository personRepository;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(path = "/saveFormData/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveFormData(@RequestBody Person newPerson) {
		Person person = personRepository.save(newPerson);
		if (person != null) {
			return new ResponseEntity<Person>(person, HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("This form cannot be saved.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "/findFormData/{id}")
	public ResponseEntity<?> findFormData(@PathVariable Integer id) {
		Optional<Person> person = personRepository.findById(id);
		if (person.isPresent()) {
			return new ResponseEntity<Person>(person.get(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("This person has not submitted their declaration form.", HttpStatus.BAD_REQUEST);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "/findAll/")
	public List<Person> findAll() {
		return (List<Person>) personRepository.findAll();
	}

}
