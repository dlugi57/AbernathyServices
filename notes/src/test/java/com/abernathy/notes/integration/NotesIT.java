package com.abernathy.notes.integration;

import com.abernathy.notes.dao.NotesDao;
import com.abernathy.notes.model.Note;
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
public class NotesIT {

    @Autowired
    NotesDao notesDao;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void addNote() throws Exception {
        Note note = new Note();
        note.setPatientId(3);
        note.setNote("test test IT");
        LocalDate date = LocalDate.of(1988, 3, 18);
        note.setUpdateDate(date);

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/patHistory/add")
                .contentType("application/json")
                .content("{\"patientId\":\"1\",\"note\":\"test note\"}"))
                .andExpect(status().isCreated());


        Optional<Note> noteAdd = notesDao.findById("603429806bf78d08faabfbc6");
        note.setId("603429806bf78d08faabfbc6");
        //assert noteAdd.orElse(null) != null;
        assertNotNull(noteAdd.orElse(null));
        assertThat(mapper.writeValueAsString(note)).isEqualTo(mapper.writeValueAsString(noteAdd.orElse(null)));
    }

}
