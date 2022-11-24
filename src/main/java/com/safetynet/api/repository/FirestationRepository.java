package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.Error.ErrorAlreadyExistException;
import com.safetynet.api.Error.ErrorNoExistException;
import com.safetynet.api.model.Firestation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class FirestationRepository {

    Logger logger = LoggerFactory.getLogger(FirestationRepository.class);

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

    /**
     * Add Firestation to the List
     *
     * @param firestation
     * @return "Firestation add !" if creation is successful or "Firestation already exist"
     */
    public String createFirestation(Firestation firestation) {
        logger.debug("Call FirestationRepository.createFirestation witn param = " + firestation.toString());
        try {
            if (!firestationMap.containsKey(firestation.getAddress())) {
                firestationMap.put(firestation.getAddress(), firestation);
                return "Firestation add !";
            } else {
                throw new ErrorAlreadyExistException("Firestation Already exist");
            }
        } catch (ErrorAlreadyExistException e) {
            logger.error("Firestation already exist");
            return "Firestation already exist";
        }
    }

    /**
     * Delete Firestation to the List.
     *
     * @param firestation
     * @return "Firestation delete !" if deletion is successful or "Firestation doesn't exist !"
     */
    public String deleteFirestation(Firestation firestation) {
        logger.debug("Call FirestationRepository.deleteFirestation witn param = " + firestation.toString());
        try {
            if (firestationMap.containsKey(firestation.getAddress())) {

                firestationMap.remove(firestation.getAddress());

                return "Firestation delete !";
            } else {
                throw new ErrorNoExistException("Firestation doesn't exist !");
            }
        } catch (ErrorNoExistException e) {
            logger.error("Firestation doesn't exist !");
            return "Firestation doesn't exist !";
        }
    }

    /**
     * Update Firestation to the List.
     *
     * @param firestation
     * @return "Firestation update !" if update is successful or "Firestation doesn't exist !"
     */
    public String updateFiresation(Firestation firestation) {
        logger.debug("Call FirestationRepository.updateFiresation witn param = " + firestation.toString());
        try {
            if (firestationMap.containsKey(firestation.getAddress())) {
                firestationMap.put(firestation.getAddress(), firestation);
                return "Firestation update !";
            } else {
                throw new ErrorNoExistException("Firestation doesn't exist !");
            }
        } catch (ErrorNoExistException e) {
            logger.error("Firestation doesn't exist !");
            return "Firestation doesn't exist !";
        }
    }


    /**
     * Find Firestation at an address
     *
     * @param address
     * @return the Firestation at the address request
     */
    public Firestation getFirestationByAddress(String address) {
        logger.debug("Call firestationRepository.getFirestationByAddress with param = " + address);

        List<Firestation> firestationList = new ArrayList<>(firestationMap.values());
        for (Firestation firestation : firestationList) {
            if (firestation.getAddress().equals(address)) {
                return firestation;
            }
        }
        return null;
    }

    /**
     * Find Firestation with this sation number
     *
     * @param stationNumber
     * @return List of firestation with this stationNumber
     */
    public List<Firestation> getFirestationsByNumber(int stationNumber) {
        logger.debug("Call firestationRepository.getFirestationsByNumber with param = " + stationNumber);

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



