package com.abernathy.patient.controllers;

import com.abernathy.patient.model.Patient;
import com.abernathy.patient.model.PatientSex;
import com.abernathy.patient.services.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService service;

    @Test
    void addPatient() throws Exception {
        when(service.addPatient(any(Patient.class))).thenReturn(true);

        this.mockMvc.perform(post("/patient/add")
                .content("{\"firstName\":\"UserFirstName\",\"lastName\":\"UserLastName\"," +
                        "\"birthDate\":\"17-10-2012\", \"sex\":\"M\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(content().string("true"))
                .andExpect(status().isCreated());

    }

    @Test
    void addUserInvalid() throws Exception {

        when(service.addPatient(any(Patient.class))).thenReturn(false);

        this.mockMvc.perform(post("/patient/add")
                .content("{\"firstName\":\"UserFirstName\",\"lastName\":\"UserLastName\"," +
                        "\"birthDate\":\"17-10-2012\", \"sex\":\"M\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void updatePatient() throws Exception {
        when(service.updatePatient(any(Patient.class))).thenReturn(true);

        this.mockMvc.perform(put("/patient/update")
                .content("{\"id\":1,\"firstName\":\"UserFirstName\"," +
                        "\"lastName\":\"UserLastName\"," +
                        "\"birthDate\":\"17-10-2012\", \"sex\":\"M\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(content().string("true"))
                .andExpect(status().isCreated());
    }

    @Test
    void updatePatientInvalid() throws Exception {
        when(service.updatePatient(any(Patient.class))).thenReturn(false);

        this.mockMvc.perform(put("/patient/update")
                .content("{\"id\":1,\"firstName\":\"UserFirstName\"," +
                        "\"lastName\":\"UserLastName\"," +
                        "\"birthDate\":\"17-10-2012\", \"sex\":\"M\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePatient() throws Exception {
        when(service.deletePatient(anyInt())).thenReturn(true);

        this.mockMvc.perform(delete("/patient/delete")
                .param("id", "1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(content().string("true"))
                .andExpect(status().isOk());
    }

    @Test
    void deletePatientInvalid() throws Exception {
        when(service.deletePatient(anyInt())).thenReturn(false);

        this.mockMvc.perform(delete("/patient/delete")
                .param("id", "1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void getPatient() throws Exception {

        Patient patient = new Patient();
        patient.setFirstName("Piotr");
        patient.setLastName("Dlugosz");
        LocalDate lt = LocalDate.now();
        patient.setBirthDate(lt);
        patient.setSex(PatientSex.F);
        patient.setPhone("123456789");
        patient.setAddress("address");

        when(service.getPatient(anyInt())).thenReturn(java.util.Optional.of(patient));

        this.mockMvc.perform(get("/patient/get")
                .param("id", String.valueOf(3))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Dlugosz"))
                .andExpect(jsonPath("$.firstName").value("Piotr"));

    }

    @Test
    void getPatientInvalid() throws Exception {

        this.mockMvc.perform(get("/patient/get")
                .param("id", String.valueOf(3))
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPatients() throws Exception {
        Patient patient = new Patient();
        patient.setFirstName("Piotr");
        patient.setLastName("Dlugosz");
        LocalDate lt = LocalDate.now();
        patient.setBirthDate(lt);
        patient.setSex(PatientSex.F);
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        patients.add(patient);
        patients.add(patient);
        when(service.getPatients()).thenReturn(patients);

        this.mockMvc.perform(get("/patient/getAll")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    void getPatientsNull() throws Exception {

        when(service.getPatients()).thenReturn(null);

        this.mockMvc.perform(get("/patient/getAll")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPatientsEmpty() throws Exception {

        when(service.getPatients()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/patient/getAll")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}