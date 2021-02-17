package com.abernathy.report.proxies;

import com.abernathy.report.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@FeignClient(name = "patient", url = "${CLIENT_REWARDS_BASE_URL:http://localhost:8084}")
public interface PatientProxy {

    @PostMapping(value = "/patient/add")
    @ResponseStatus(HttpStatus.CREATED)
    boolean addPatient(@Valid @RequestBody Patient patient);

    /**
     * Update patient
     *
     * @param patient patient object
     * @return status and uri with new created patient
     */
    @PutMapping(value = "/patient/update")
    @ResponseStatus(HttpStatus.CREATED)
    boolean updatePatient(@Valid @RequestBody Patient patient);

    /**
     * Delete patient
     *
     * @param id id of patient
     */
    @DeleteMapping(value = "/patient/delete")
    @ResponseStatus(HttpStatus.OK)
    boolean deletePatient(@RequestParam Integer id);

    /**
     * Get patient
     *
     * @param id patient id
     * @return patient object
     */
    @GetMapping(value = "/patient/get")
    @ResponseStatus(HttpStatus.OK)
    Patient getPatient(@RequestParam() Integer id);

    /**
     * Get all patients
     *
     * @return List of Patient
     */
    @GetMapping(value = "/patient/getAll")
    List<Patient> getPatients();


}
