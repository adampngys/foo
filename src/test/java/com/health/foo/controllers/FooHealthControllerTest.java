package com.health.foo.controllers;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import java.awt.print.Printable;
import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hibernate.event.spi.PostCollectionRecreateEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.foo.models.Person;
import com.health.foo.repositories.PersonRepository;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FooHealthController.class)
class FooHealthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	FooHealthController controller;

	@MockBean
	PersonRepository personRepository;

	// unable to save a new form
	@Test
	public void shouldNotCreateForm() throws Exception {		
		Person person = new Person("Tiff", "36", "s5,s6,s8", "no");
		//System.out.println(objectMapper.writeValueAsString(person));
		mockMvc.perform(post("/api/saveFormData/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Tiff\",\"temperature\":\"36\",\"symptoms\":\"s5,s6,s8\",\"contact\":\"no\"}"))
//				.content("{\"id\":4,\"name\":\"Tiff\",\"temperature\":\"36\",\"symptoms\":\"s5,s6,s8\",\"contact\":\"no\"}"))
//				.content(objectMapper.writeValueAsString(person)))
				.andExpect(status().is5xxServerError())
				.andDo(print());
	}
	
	// return form based on id
	@Test
	public void shouldReturnForm() throws Exception {
		Integer id = 3;		
		Person person = new Person("tim", "36.60", "s5,s6", "no");
		Mockito.when(personRepository.findById(id)).thenReturn(Optional.of(person));		
		mockMvc.perform(get("/api/findFormData/{id}", id)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(person.getName()))
				.andDo(print());
	}
	
	// return list of forms in data table
	@Test
	public void testFindAll() throws Exception {		
		List<Person> persons = Arrays.asList(
				new Person("Adam", "37.00", "s1,s2,s3,s4,s5,s6,s7,s8,s9,s10", "Yes"),
				new Person("Mary", "38.00", "s1,s2,s6,s7,s8", "No"),
				new Person("tim","36.60","s5,s6","no"));
		Mockito.when(personRepository.findAll()).thenReturn(persons);				
		mockMvc.perform(get("/api/findAll/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(persons.size()))
				.andDo(print());
	}

}
