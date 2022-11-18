package com.safetynet.api.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.param;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.logging.Logger;


@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    PersonService personService;


    @Test
    void createPerson() throws Exception {

        Person person0 = new Person("firstName0", "lastName0", "address0", "city0", 0, "phone0", "0@0");
        ObjectMapper objectMapperapper = new ObjectMapper();
        String requestJson = objectMapperapper.writeValueAsString(person0);

        when(personService.createPerson(any())).thenReturn("OK");

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(requestJson));


    }

    @Test
    void updatePerson() {
    }

    @Test
    void deletePerson() {
    }

    @Test
    void childAlertAndFamilyByAdress() {
    }

    @Test
    void getPersonMedicalRecordAndStationNumber() {
    }

    @Test
    void getPersonInfo() {
    }

    @Test
    void getEmailByCity() {
    }
}