package com.abernathy.patient.controllers;

import com.abernathy.patient.model.Patient;
import com.abernathy.patient.services.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
public class PatientController {
    static final Logger logger = LogManager
            .getLogger(PatientController.class);

    // Service initialization
    PatientService patientService;

    /**
     * Field injection of patient service
     *
     * @param patientService initialization of patients service
     */
    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Connect user
     *
     * @param id    email
     * @return User object
     */
    @GetMapping(value = "/getPatient")
    @ResponseStatus(HttpStatus.OK)
    public Patient getPatient(@RequestParam() Integer id) {
        // get person
        Optional<Patient> patient = patientService.getPatient(id);
        // if person don't exist send error message
        if (patient == null) {
            logger.error("GET user -> connectUser /**/ HttpStatus : " + HttpStatus.NOT_FOUND + " " +
                    "/**/ Message :  User email " + id + " are wrong");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Wrong email: " + patient );
        }

       logger.info("GET person -> connectUser /**/ HttpStatus : " + HttpStatus.OK + " /**/ " +
                "Result : '{}'.", patient.toString());
        return patient.get();
    }


    /**
     * Get all patients
     *
     * @return List of Patient
     */
    @GetMapping(value = "/getPatients")
    public List<Patient> getPatients() {
        List<Patient> patients = patientService.getPatients();

        if (patients == null || patients.isEmpty()) {

            logger.error("GET patients -> getPatients /**/ Result : " + HttpStatus.NOT_FOUND + " /**/ " +
                    "Message : There is no patients in the data base");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no patients in the " +
                    "data base");
        }
        logger.info("GET patients -> getPatients /**/ HttpStatus : " + HttpStatus.OK + " /**/ Result : '{}'.", patients.toString());

        return patients;
    }
}
