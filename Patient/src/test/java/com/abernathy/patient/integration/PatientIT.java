package com.abernathy.patient.integration;

import com.abernathy.patient.dao.PatientDao;
import com.abernathy.patient.model.Patient;
import com.abernathy.patient.model.PatientSex;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class PatientIT {

    @Autowired
    PatientDao patientDao;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    // TODO: 12/02/2021
    //@Test
    public void addUser() throws Exception {
        Patient patient = new Patient();
        patient.setFirstName("Piotr");
        patient.setLastName("Dlugosz");
        LocalDate lt = LocalDate.now();
        patient.setBirthDate(lt);
        patient.setSex(PatientSex.F);
        patient.setPhone("123456789");
        patient.setAddress("address");

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType("application/json")
                .content(mapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());


        Optional<Patient> patientAdd = patientDao.findById(patient.getId());
        patient.setId(6);
        assert patientAdd.orElse(null) != null;
        assertNotNull(patientAdd.orElse(null));
        assertThat(mapper.writeValueAsString(patient)).isEqualTo(mapper.writeValueAsString(patientAdd.orElse(null)));
    }

}
