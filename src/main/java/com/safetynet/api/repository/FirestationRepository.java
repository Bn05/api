package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.Firestation;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class FirestationRepository {

    private Map<String, Firestation> firestationMap = new HashMap<>();

    public void readJsonFile() throws IOException {

        List<Firestation> firestationList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File("src/main/resources/data.json")).get("firestations");

        for (JsonNode firestation : jsonNode) {
            firestationList.add(new Firestation(
                    firestation.path("address").asText(),
                    firestation.path("station").asInt()
            ));
        }
        for (Firestation firestation : firestationList){
            firestationMap.put(firestation.getAddress(), firestation);
        }


    }

    public Map<String, Firestation> getFirestationMap (){
        return firestationMap;
    }

    public String  createFirestation (Firestation firestation){
        firestationMap.put(firestation.getAddress(), firestation);

        return "Firestation add !";
    }
}

