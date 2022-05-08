package com.health.foo.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PersonTest {

	@InjectMocks
	private Person person;
	
	@BeforeEach
	void init() {
		person = new Person();
		person.setId(1);
		person.setName("Aaron");
		person.setSymptoms("s1,s2,s5");
		person.setContact("no");
	}
	
	@Test
	void testGetId() {
		Assertions.assertEquals(1,person.getId());
	}
	
	@Test
	void testGetName() {
		Assertions.assertEquals("Aaron",person.getName());
	}
	
	@Test
	void testGetSymptoms() {
		Assertions.assertEquals("s1,s2,s5",person.getSymptoms());
	}

	@Test
	void testGetContact() {
		Assertions.assertEquals("no",person.getContact());
	}
}
