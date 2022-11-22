package com.safetynet.api.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(person0);

        when(personService.createPerson(any())).thenReturn("OK");

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        verify(personService, times(1)).createPerson(any());
    }

    @Test
    void updatePerson() throws Exception {

        Person person0 = new Person("firstName0", "lastName0", "address0", "city0", 0, "phone0", "0@0");
        ObjectMapper objectMapperapper = new ObjectMapper();
        String requestJson = objectMapperapper.writeValueAsString(person0);

        when(personService.updatePerson(any())).thenReturn("OK");

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(requestJson));

        verify(personService, times(1)).updatePerson(any());
    }

    @Test
    void deletePerson() throws Exception {

        when(personService.deletePerson(any(), any())).thenReturn("OK");

        mockMvc.perform(delete("/person")
                .param("firstName", "firstName0")
                .param("lastName", "lastName0"));


        verify(personService, times(1)).deletePerson(any(), any());
    }

    @Test
    void childAlertAndFamilyByAdress() throws Exception {

        List<Object> returnList = new ArrayList<>();

        when(personService.getChildAlertAndFamilyByAddress(any())).thenReturn(returnList);

        mockMvc.perform(get("/childAlert")
                .param("address", "address"));

        verify(personService, times(1)).getChildAlertAndFamilyByAddress(any());
    }

    @Test
    void getPersonMedicalRecordAndStationNumber() throws Exception {
        Map<String, Object> returnMap = new HashMap<>();

        when(personService.getPersonMedicalRecordAndStationNumber(any())).thenReturn(returnMap);

        mockMvc.perform(get("/fire")
                .param("address", "address"));

        verify(personService,times(1)).getPersonMedicalRecordAndStationNumber(any());
    }

    @Test
    void getPersonInfo() throws Exception {
        List<Map<String, String>> returnList = new ArrayList<>();

        when(personService.getPersonInfo(any(), any())).thenReturn(returnList);

        mockMvc.perform(get("/personInfo")
                .param("firstName", "firstName")
                .param("lastName", "lastName"));

        verify(personService,times(1)).getPersonInfo(any(),any());
    }

    @Test
    void getEmailByCity() throws Exception {
        List<String> returnList = new ArrayList<>();

        when(personService.getEmailByCity(any())).thenReturn(returnList);

        mockMvc.perform(get("/communityEmail")
                .param("city","city"));

        verify(personService,times(1)).getEmailByCity(any());
    }
}