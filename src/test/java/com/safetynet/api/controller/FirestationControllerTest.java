package com.safetynet.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.Firestation;
import com.safetynet.api.service.FirestationService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class FirestationControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    FirestationService firestationService;

    @Test
    void getPersonsByStationNumber() throws Exception {

        List<Object> returnList = new ArrayList<>();

        when(firestationService.getPersonsByStationNumber(anyInt())).thenReturn(returnList);

        mockMvc.perform(get("/firestation")
                .param("stationNumber", "0"));

        verify(firestationService, times(1)).getPersonsByStationNumber(anyInt());
    }

    @Test
    void getPhoneNumberByStationNumber() throws Exception {

        List<String> returnList = new ArrayList<>();

        when(firestationService.getPhoneNumberByStationNumber(anyInt())).thenReturn(returnList);

        mockMvc.perform(get("/phoneAlert")
                .param("firestation", "0"));

        verify(firestationService, times(1)).getPhoneNumberByStationNumber(anyInt());

    }

    @Test
    void getFamilyByFireStation() throws Exception {
        Map<String, List<String>> returnMap = new HashMap<>();

        when(firestationService.getFamilyByFireStation(anyList())).thenReturn(returnMap);

        mockMvc.perform(get("/flood/stations")
                .param("stations", "1,2"));

        verify(firestationService, times(1)).getFamilyByFireStation(anyList());

    }

    @Test
    void createFirestation() throws Exception {
        Firestation firestation = new Firestation("address", 0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(firestation);

        when(firestationService.createFirestation(any())).thenReturn("ok");

        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        verify(firestationService, times(1)).createFirestation(any());
    }

    @Test
    void updateFiresation() throws Exception {
        Firestation firestation = new Firestation("address", 0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(firestation);

        when(firestationService.updateFiresation(any())).thenReturn("ok");

        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        verify(firestationService, times(1)).updateFiresation(any());
    }


    @Test
    void deleteFirestation() throws Exception {

        Firestation firestation = new Firestation("address", 0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(firestation);

        when(firestationService.deleteFirestation(any())).thenReturn("ok");

        mockMvc.perform(delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        verify(firestationService, times(1)).deleteFirestation(any());
    }
}