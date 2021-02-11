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

    /**
     * Get patient
     *
     * @param id patient id
     * @return patient object
     */
    @Override
    public Optional<Patient> getPatient(Integer id) {
        return patientDao.findById(id);
    }

    /**
     * Add patient
     *
     * @param patient patient object
     * @return true when success
     */
    @Override
    public boolean addPatient(Patient patient) {
        try {
            if (patientDao.save(patient).getId() > 0) {
                return true;
            }
        } catch (Exception e) {
            logger.info(e.toString());
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
        if (patientDao.existsById(patient.getId())) {

            return patientDao.save(patient).getId() > 0;

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
        if (patientDao.existsById(id)) {
            try {
                patientDao.deleteById(id);

                return true;

            } catch (Exception e) {
                logger.info(e.toString());
            }
        }
        return false;
    }


}
