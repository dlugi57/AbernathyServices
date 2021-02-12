package com.abernathy.patient.services;

import com.abernathy.patient.dao.PatientDao;
import com.abernathy.patient.model.Patient;
import com.abernathy.patient.model.PatientSex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class PatientServiceImplTest {

    @TestConfiguration
    static class PatientServiceImplTestContextConfiguration {
        @Bean
        public PatientService patientService() {
            return new PatientServiceImpl();
        }
    }

    @Resource
    PatientService patientService;

    @MockBean
    PatientDao patientDao;

    @Test
    void getPatients() {
        Patient patient = new Patient();
        patient.setFirstName("Piotr");
        patient.setLastName("Dlugosz");
        LocalDate lt = LocalDate.now();
        patient.setBirthDate(lt);
        patient.setSex(PatientSex.F);
        patient.setPhone("123456789");
        patient.setAddress("address");
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        patients.add(patient);
        patients.add(patient);

        // GIVEN
        given(patientDao.findAll()).willReturn(patients);
        // WHEN
        List<Patient> patientsTest = patientService.getPatients();
        // THEN
        verify(patientDao, times(1)).findAll();
        assertThat(patientsTest.size()).isEqualTo(3);

    }

    @Test
    void getPatients_Invalid() {

        // GIVEN
        given(patientDao.findAll()).willReturn(Collections.emptyList());
        // WHEN
        List<Patient> patientsTest = patientService.getPatients();
        // THEN
        verify(patientDao, times(1)).findAll();
        assertThat(patientsTest).isEqualTo(Collections.emptyList());

    }

    @Test
    void getPatient() {
        Patient patient = new Patient();
        patient.setFirstName("Piotr");
        patient.setLastName("Dlugosz");
        LocalDate lt = LocalDate.now();
        patient.setBirthDate(lt);
        patient.setSex(PatientSex.F);
        patient.setPhone("123456789");
        patient.setAddress("address");
        // GIVEN
        patient.setId(1);
        given(patientDao.findById(anyInt())).willReturn(Optional.of(patient));

        // WHEN
        Optional<Patient> patientTest = patientService.getPatient(1);

        // THEN
        verify(patientDao, times(1)).findById(anyInt());

        assertThat(patientTest).isEqualTo(Optional.of(patient));
    }

    @Test
    void getPatient_Invalid() {

        // GIVEN
        given(patientDao.findById(anyInt())).willReturn(null);

        // WHEN
        Optional<Patient> patientTest = patientService.getPatient(1);

        // THEN
        verify(patientDao, times(1)).findById(anyInt());

        assertThat(patientTest).isEqualTo(null);
    }

    @Test
    void addPatient() {
        Patient patient = new Patient();
        patient.setFirstName("Piotr");
        patient.setLastName("Dlugosz");
        LocalDate lt = LocalDate.now();
        patient.setBirthDate(lt);
        patient.setSex(PatientSex.F);
        patient.setPhone("123456789");
        patient.setAddress("address");
        // GIVEN
        patient.setId(1);
        given(patientDao.save(any(Patient.class))).willReturn(patient);
        // WHEN
        boolean userTest = patientService.addPatient(patient);
        // THEN
        verify(patientDao, times(1)).save(any(Patient.class));

        assertThat(userTest).isEqualTo(true);
    }

    @Test
    void addPatient_Invalid() {
        Patient patient = new Patient();
        patient.setFirstName("Piotr");
        patient.setLastName("Dlugosz");
        LocalDate lt = LocalDate.now();
        patient.setBirthDate(lt);
        patient.setSex(PatientSex.F);
        patient.setPhone("123456789");
        patient.setAddress("address");
        // GIVEN
        patient.setId(1);
        given(patientDao.save(any(Patient.class))).willReturn(null);
        // WHEN
        boolean patientTest = patientService.addPatient(patient);
        // THEN
        verify(patientDao, times(1)).save(any(Patient.class));

        assertThat(patientTest).isEqualTo(false);
    }


    @Test
    void updatePatient() {
        Patient patient = new Patient();
        patient.setFirstName("Piotr");
        patient.setLastName("Dlugosz");
        LocalDate lt = LocalDate.now();
        patient.setBirthDate(lt);
        patient.setSex(PatientSex.F);
        patient.setPhone("123456789");
        patient.setAddress("address");
        // GIVENint
        given(patientDao.existsById(any())).willReturn(true);
        patient.setId(1);
        given(patientDao.save(any(Patient.class))).willReturn(patient);
        // WHEN
        boolean patientTest = patientService.updatePatient(patient);
        // THEN
        verify(patientDao, times(1)).existsById(anyInt());
        verify(patientDao, times(1)).save(any(Patient.class));

        assertThat(patientTest).isEqualTo(true);
    }

    @Test
    void updatePatient_Invalid() {
        Patient patient = new Patient();
        patient.setFirstName("Piotr");
        patient.setLastName("Dlugosz");
        LocalDate lt = LocalDate.now();
        patient.setBirthDate(lt);
        patient.setSex(PatientSex.F);
        patient.setPhone("123456789");
        patient.setAddress("address");
        // GIVENint
        given(patientDao.existsById(any())).willReturn(false);
        patient.setId(1);
        // WHEN
        boolean patientTest = patientService.updatePatient(patient);
        // THEN
        verify(patientDao, times(1)).existsById(anyInt());

        assertThat(patientTest).isEqualTo(false);
    }

    @Test
    void deletePatient() {

        // GIVEN
        given(patientDao.existsById(anyInt())).willReturn(true);

        // WHEN
        boolean patientTest = patientService.deletePatient(1);
        // THEN
        verify(patientDao, times(1)).existsById(anyInt());
        verify(patientDao, times(1)).deleteById(anyInt());

        assertThat(patientTest).isEqualTo(true);
    }

    @Test
    void deletePatient_Invalid() {

        // GIVEN
        given(patientDao.existsById(anyInt())).willReturn(false);

        // WHEN
        boolean patientTest = patientService.deletePatient(1);
        // THEN
        verify(patientDao, times(1)).existsById(anyInt());

        assertThat(patientTest).isEqualTo(false);
    }
}