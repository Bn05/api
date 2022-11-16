package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.Error.ErrorAlwaysExistException;
import com.safetynet.api.Error.ErrorNoExistException;
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

    public Map<String, Firestation> getFirestationMap() {
        return firestationMap;
    }

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
        try {
            if (!firestationMap.containsKey(firestation.getAddress())) {
                firestationMap.put(firestation.getAddress(), firestation);
                return "Firestation add !";
            } else {
                throw new ErrorAlwaysExistException("Firestation always exist");
            }
        } catch (ErrorAlwaysExistException e) {
            return "Firestation always exist";
        }
    }

    public String deleteFirestation(Firestation firestation) {
        try {
            if (firestationMap.containsKey(firestation.getAddress())) {

                firestationMap.remove(firestation.getAddress());

                return "Firestation delete !";
            } else {
                throw new ErrorNoExistException("Firestation doesn't exist !");
            }
        } catch (ErrorNoExistException e) {
            return "Firestation doesn't exist !";
        }
    }

    public String updateFiresation(Firestation firestation) {
        try {
            if (firestationMap.containsKey(firestation.getAddress())) {
                firestationMap.put(firestation.getAddress(), firestation);
                return "Firestation update !";
            } else {
                throw new ErrorNoExistException("Firestation doesn't exist !");
            }
        } catch (ErrorNoExistException e) {
            return "Firestation doesn't exist !";
        }
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



