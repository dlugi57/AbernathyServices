package com.abernathy.report.services;

import com.abernathy.report.model.Patient;
import com.abernathy.report.proxies.PatientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    static final Logger logger = LogManager
            .getLogger(PatientServiceImpl.class);

    @Autowired
    PatientProxy patientProxy;

    /**
     * Get all patients
     *
     * @return list of patients
     */
    @Override
    public List<Patient> getPatients() {

        try {

            return patientProxy.getPatients();

        } catch (Exception e) {
            logger.info(e.toString());
        }
        return null;
    }

    /**
     * Get patient
     *
     * @param id patient id
     * @return patient object
     */
    @Override
    public Patient getPatient(Integer id) {

        try {
            return patientProxy.getPatient(id);

        } catch (Exception e) {
            logger.info(e.toString());
        }
        return null;
    }

    /**
     * Add patient
     *
     * @param patient patient object
     * @return true when success
     */
    @Override
    public boolean addPatient(Patient patient) {
        if (patientProxy.addPatient(patient)) {
            return true;

        }
        return false;
    }

    /**
     * Update patient
     *
     * @param patient patient object
     * @return true when success
     */
    @Override
    public boolean updatePatient(Patient patient) {
        if (patientProxy.updatePatient(patient)) {

            return true;

        }
        return false;
    }

    /**
     * Delete patient
     *
     * @param id patient object
     * @return true when success
     */
    @Override
    public boolean deletePatient(Integer id) {
        if (patientProxy.deletePatient(id)) {
            return true;
        }
        return false;
    }


}
