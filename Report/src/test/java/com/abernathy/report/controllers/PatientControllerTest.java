package com.abernathy.report.controllers;

import com.abernathy.report.model.Patient;
import com.abernathy.report.model.PatientSex;
import com.abernathy.report.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    static String firstname = "test";
    static String lastname = "test";
    static LocalDate birthdate = LocalDate.parse("17-03-2020", DateTimeFormatter.ofPattern("dd" +
            "-MM-yyyy"));
    static PatientSex sex = PatientSex.M;
    static String address = "test";
    static String phone = "test";



    @Test
    void home() throws Exception {
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
        //WHEN
        when(patientService.getPatients()).thenReturn(patientList);

        //THEN
        mockMvc.perform(get("/patient/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("patient/list"));
    }

    @Test
    void addPatientForm() throws Exception {
        //WHEN //THEN
        mockMvc.perform(get("/patient/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("patient/add"));
    }

    @Test
    void validate() throws Exception {

        //GIVEN
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedString = birthdate.format(formatter);
        // WHEN
        when(patientService.addPatient(any(Patient.class))).thenReturn(true);

        //THEN
        mockMvc.perform(post("/patient/validate")
                .param("firstName", firstname)
                .param("lastName", lastname)
                .param("sex", String.valueOf(sex))
                .param("birthDate", formattedString))
                .andDo(print())
                .andExpect(view().name("redirect:/patient/list"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void validate_invalid() throws Exception {

        //GIVEN
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
        String formattedString = birthdate.format(formatter);
        // WHEN
        when(patientService.addPatient(any(Patient.class))).thenReturn(false);

        //THEN
        mockMvc.perform(post("/patient/validate")
                .param("firstName", firstname)
                .param("lastName", lastname)
                .param("sex", String.valueOf(sex))
                .param("birthDate", formattedString))
                .andDo(print())
                .andExpect(view().name("patient/add"))
                .andExpect(status().isOk());
    }

    @Test
    void showUpdateForm() throws Exception {
        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);
        //WHEN
        when(patientService.getPatient(anyInt())).thenReturn(patientTest);

        //THEN
        mockMvc.perform(get("/patient/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("patient/update"));
    }

    @Test
    void updatePatient() throws Exception {
        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedString = birthdate.format(formatter);
        //WHEN
        when(patientService.addPatient(any(Patient.class))).thenReturn(true);

        //THEN
        mockMvc.perform(post("/patient/update/1")
                .param("firstName", firstname)
                .param("lastName", lastname)
                .param("sex", String.valueOf(sex))
                .param("birthDate", formattedString))
                .andDo(print())
                .andExpect(view().name("redirect:/patient/list"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updatePatient_Invalid() throws Exception {
        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
        String formattedString = birthdate.format(formatter);
        //WHEN
        //when(patientService.addPatient(anyInt())).thenReturn(Optional.of(bidListTest));

        //THEN
        mockMvc.perform(post("/patient/update/1")
                .param("firstName", firstname)
                .param("lastName", lastname)
                .param("sex", String.valueOf(sex))
                .param("birthDate", formattedString))
                .andDo(print())
                .andExpect(view().name("patient/update"))
                .andExpect(status().isOk());
    }

    @Test
    void deletePatient() throws Exception {
        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);

        //WHEN
        when(patientService.getPatient(anyInt())).thenReturn(patientTest);

        //THEN
        mockMvc.perform(get("/patient/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patient/list"));

        verify(patientService, times(1)).deletePatient(anyInt());
    }
}