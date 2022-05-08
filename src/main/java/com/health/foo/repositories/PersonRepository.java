package com.health.foo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.health.foo.models.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{

}