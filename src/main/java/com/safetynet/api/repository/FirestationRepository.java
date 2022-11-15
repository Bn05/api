package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.Firestation;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
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
        for (Firestation firestation : firestationList) {
            firestationMap.put(firestation.getAddress(), firestation);
        }
    }

    public String createFirestation(Firestation firestation) {
        firestationMap.put(firestation.getAddress(), firestation);

        //TODO : ajout erreur si firestation deja existante

        return "Firestation add !";
    }

    public String deleteFirestation(String firestation) {

        firestationMap.remove(firestation);

        // TODO : ajout erreur si firesation innexistante

        return "Firestation delete !";
    }

    public String updateFiresation(Firestation firestation) {

        firestationMap.put(firestation.getAddress(), firestation);

        //TODO : ajout erreur si firestation inexistante

        return "Firestation update !";
    }

    public Firestation getFirestationByAddress(String address) {

        List<Firestation> firestationList = new ArrayList<>(firestationMap.values());
        for (Firestation firestation : firestationList) {
            if (firestation.getAddress().equals(address)) {
                return firestation;
            }
        }
        return null;
    }

    public List<Firestation> getFirestationsByNumber(int stationNumber) {

        List<Firestation> firestationList = new ArrayList<>(firestationMap.values());
        List<Firestation> firestationSelectList = new ArrayList<>();
        for (Firestation firestation : firestationList) {
            if (firestation.getStation() == stationNumber) {
                firestationSelectList.add(firestation);
            }
        }
        return firestationSelectList;
    }
}



