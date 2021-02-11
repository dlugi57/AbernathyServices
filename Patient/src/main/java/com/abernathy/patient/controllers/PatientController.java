package com.abernathy.patient.controllers;

import com.abernathy.patient.model.Patient;
import com.abernathy.patient.services.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
     * Add patient
     *
     * @param patient patient object
     * @return status and uri with new created patient
     */
    @PostMapping(value = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addPatient(@Valid @RequestBody Patient patient) {

        // if user already exist send status and error message
        if (!patientService.addPatient(patient)) {
            logger.error("POST patient -> " +
                    "addPatient /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/ Message : " +
                    " This patient already exist");

            throw new ResponseStatusException(HttpStatus.CONFLICT, "This patient already exist");
        }
        // create url with new created medical record
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", patient.getFirstName())
                .queryParam("lastName", patient.getLastName()).build().toUri();

        logger.info("POST patient -> addPatient /**/ HttpStatus : " + HttpStatus.CREATED + " /**/ " +
                "Result : '{}'.", location);

        return ResponseEntity.created(location).build();
    }

    /**
     * Add patient
     *
     * @param patient patient object
     * @return status and uri with new created patient
     */
    @PutMapping(value = "/update")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> updatePatient(@Valid @RequestBody Patient patient) {

        // if user already exist send status and error message
        if (!patientService.updatePatient(patient)) {
            logger.error("POST patient -> " +
                    "updatePatient /**/ HttpStatus : " + HttpStatus.NOT_FOUND + " /**/ Message : " +
                    " This patient don't exist");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user don't exist");
        }
        // create url with new created medical record
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", patient.getFirstName())
                .queryParam("lastName", patient.getLastName()).build().toUri();

        logger.info("POST patient -> updatePatient /**/ HttpStatus : " + HttpStatus.CREATED + " /**/ " +
                "Result : '{}'.", location);

        return ResponseEntity.created(location).build();
    }

    /**
     * Delete patient
     *
     * @param id id of patient
     */
    @DeleteMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deletePatient(@RequestParam Integer id) {
        // if there is no patient send status and error message
        if (!patientService.deletePatient(id)) {
            logger.error("DELETE patient -> deletePatient /**/ Result : " + HttpStatus.NOT_FOUND
                    + " /**/ Message : This patient don't exist : '{}'.", id.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This patient don't exist");
        }
        logger.info("DELETE patient -> deletePatient /**/ HttpStatus : " + HttpStatus.OK);
    }

    /**
     * Get patient
     *
     * @param id patient id
     * @return patient object
     */
    @GetMapping(value = "/get")
    @ResponseStatus(HttpStatus.OK)
    public Patient getPatient(@RequestParam() Integer id) {
        // get patient
        Optional<Patient> patient = patientService.getPatient(id);
        // if patient don't exist send error message
        if (patient.isEmpty()) {
            logger.error("GET patient -> getPatient /**/ HttpStatus : " + HttpStatus.NOT_FOUND + " " +
                    "/**/ Message :  patient id " + id + " is wrong");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Wrong patient: " + patient);
        }

        logger.info("GET patient -> getPatient /**/ HttpStatus : " + HttpStatus.OK + " /**/ " +
                "Result : '{}'.", patient.toString());
        return patient.get();
    }

    /**
     * Get all patients
     *
     * @return List of Patient
     */
    @GetMapping(value = "/getAll")
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
