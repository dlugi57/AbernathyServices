package com.abernathy.patient.dao;

import com.abernathy.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDao extends JpaRepository<Patient, Integer> {
    boolean existsByFirstName(String FirstName);

    boolean existsByLastName(String lastName);

}
