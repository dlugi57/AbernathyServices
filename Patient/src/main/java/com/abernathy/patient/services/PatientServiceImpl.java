package com.abernathy.patient.services;

import com.abernathy.patient.dao.PatientDao;
import com.abernathy.patient.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService{

    static final Logger logger = LogManager
            .getLogger(PatientServiceImpl.class);

    PatientDao patientDao;

    /**
     * Field injection of patient dao
     *
     * @param patientDao patient dao
     */
    @Autowired
    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    /**
     * Get all patients
     *
     * @return list of patients
     */
    @Override
    public List<Patient> getPatients() {
        return patientDao.findAll();
    }

    @Override
    public Optional<Patient> getPatient(Integer id) {
        return patientDao.findById(id);
    }

}
