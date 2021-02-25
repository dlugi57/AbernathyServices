package com.abernathy.report.services;

import com.abernathy.report.model.Patient;
import com.abernathy.report.model.PatientSex;
import com.abernathy.report.proxies.PatientProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class PatientServiceImplTest {

    @TestConfiguration
    static class PatientServiceImplTestTestContextConfiguration {
        @Bean
        public PatientService patientService() {
            return new PatientServiceImpl();
        }
    }

    @Resource
    PatientService patientService;

    @MockBean
    PatientProxy patientProxy;

    static String firstname = "test";
    static String lastname = "test";
    static LocalDate birthdate = LocalDate.parse("17-03-2020", DateTimeFormatter.ofPattern("dd" +
            "-MM-yyyy"));
    static PatientSex sex = PatientSex.M;
    static String address = "test";
    static String phone = "test";

    @Test
    void getPatients() {
        List<Patient> patientList = new ArrayList<>();

        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);

        patientList.add(patientTest);

        // GIVEN
        given(patientProxy.getPatients()).willReturn(patientList);
        // WHEN
        List<Patient> testPatients = patientService.getPatients();
        // THEN
        verify(patientProxy, times(1)).getPatients();
        assertThat(testPatients.size()).isEqualTo(1);

    }

    @Test
    void getPatients_Invalid() {

        // GIVEN
        given(patientProxy.getPatients()).willReturn(Collections.emptyList());
        // WHEN
        List<Patient> testPatients = patientService.getPatients();
        // THEN
        verify(patientProxy, times(1)).getPatients();
        assertThat(testPatients).isEqualTo(Collections.emptyList());

    }

    @Test
    void getPatient() {
        // GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);

        given(patientProxy.getPatient(anyInt())).willReturn(patientTest);
        // WHEN
        Patient patient = patientService.getPatient(1);
        // THEN
        verify(patientProxy, times(1)).getPatient(anyInt());
        assertThat(patient).isEqualTo(patientTest);
    }

    @Test
    void getPatient_Invalid() {
        // GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);

        given(patientProxy.getPatient(anyInt())).willReturn(null);
        // WHEN
        Patient patient = patientService.getPatient(1);
        // THEN
        verify(patientProxy, times(1)).getPatient(anyInt());
        assertThat(patient).isEqualTo(null);
    }

    @Test
    void addPatient() {
        // GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);
        given(patientProxy.addPatient(any(Patient.class))).willReturn(true);
        // WHEN
        boolean patient = patientService.addPatient(patientTest);
        // THEN
        verify(patientProxy, times(1)).addPatient(any(Patient.class));

        assertThat(patient).isEqualTo(true);
    }

    @Test
    void addPatient_Invalid() {
        // GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);
        given(patientProxy.addPatient(any(Patient.class))).willReturn(false);
        // WHEN
        boolean patient = patientService.addPatient(patientTest);
        // THEN
        verify(patientProxy, times(1)).addPatient(any(Patient.class));

        assertThat(patient).isEqualTo(false);
    }

    @Test
    void updatePatient() {
        // GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);
        given(patientProxy.updatePatient(any(Patient.class))).willReturn(true);
        // WHEN
        boolean patient = patientService.updatePatient(patientTest);
        // THEN
        verify(patientProxy, times(1)).updatePatient(any(Patient.class));

        assertThat(patient).isEqualTo(true);
    }

    @Test
    void updatePatient_Invalid() {
        // GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);
        given(patientProxy.updatePatient(any(Patient.class))).willReturn(false);
        // WHEN
        boolean patient = patientService.updatePatient(patientTest);
        // THEN
        verify(patientProxy, times(1)).updatePatient(any(Patient.class));

        assertThat(patient).isEqualTo(false);
    }

    @Test
    void deletePatient() {
        // GIVEN
        given(patientProxy.deletePatient(anyInt())).willReturn(true);
        // WHEN
        boolean patient = patientService.deletePatient(1);
        // THEN
        verify(patientProxy, times(1)).deletePatient(anyInt());

        assertThat(patient).isEqualTo(true);
    }

    @Test
    void deletePatient_Invalid() {
        // GIVEN
        given(patientProxy.deletePatient(anyInt())).willReturn(false);
        // WHEN
        boolean patient = patientService.deletePatient(1);
        // THEN
        verify(patientProxy, times(1)).deletePatient(anyInt());

        assertThat(patient).isEqualTo(false);
    }
}