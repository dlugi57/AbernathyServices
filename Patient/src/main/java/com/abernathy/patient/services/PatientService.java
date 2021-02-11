package com.abernathy.patient.services;

import com.abernathy.patient.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<Patient> getPatients();

    Optional<Patient> getPatient(Integer id);

    boolean addPatient(Patient patient);

    boolean updatePatient(Patient patient);

    boolean deletePatient(Integer id);
}
