package com.safetynet.api;

import com.safetynet.api.repository.FirestationRepository;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }


    @Autowired
    PersonRepository personRepository;
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Override
    public void run(String... args)  {

        try {
            personRepository.readJsonFile();
            firestationRepository.readJsonFile();
            medicalRecordRepository.readJsonFile();
        }catch (Exception e)
        {
            System.out.println("ERREUR");
            //TODO : Ajout log
        }
    }
}
