package com.abernathy.report.services;

import com.abernathy.report.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getPatients();

    Patient getPatient(Integer id);

    boolean addPatient(Patient patient);

    boolean updatePatient(Patient patient);

    boolean deletePatient(Integer id);
}
