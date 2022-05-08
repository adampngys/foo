package com.health.foo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.foo.repositories.PersonRepository;

@Service
public class FooHealthService {
	
	@Autowired
	PersonRepository personRepository;
}
