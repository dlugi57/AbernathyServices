package com.abernathy.notes.controllers;

import com.abernathy.notes.model.Note;
import com.abernathy.notes.services.NotesService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NotesController.class)
class NotesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotesService service;

    @Test
    void addNote() throws Exception {
        when(service.addNote(any(Note.class))).thenReturn(true);

        this.mockMvc.perform(post("/patHistory/add")
                .content("{\"patientId\":\"1\",\"note\":\"test note\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(content().string("true"))
                .andExpect(status().isCreated());

    }

    @Test
    void addNoteInvalid() throws Exception {

        when(service.addNote(any(Note.class))).thenReturn(false);

        this.mockMvc.perform(post("/patHistory/add")
                .content("{\"patientId\":\"1\",\"note\":\"test note\"}")

                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void updateNote() throws Exception {
        when(service.updateNote(any(Note.class))).thenReturn(true);

        this.mockMvc.perform(put("/patHistory/update")

                .content("{\"id\":1,\"patientId\":\"1\",\"note\":\"test note\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(content().string("true"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateNoteInvalid() throws Exception {
        when(service.updateNote(any(Note.class))).thenReturn(false);

        this.mockMvc.perform(put("/patHistory/update")
                .content("{\"id\":1,\"patientId\":\"1\",\"note\":\"test note\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNote() throws Exception {
        when(service.deleteNote(anyString())).thenReturn(true);

        this.mockMvc.perform(delete("/patHistory/delete")
                .param("id", "1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(content().string("true"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNoteInvalid() throws Exception {
        when(service.deleteNote(anyString())).thenReturn(false);

        this.mockMvc.perform(delete("/patHistory/delete")
                .param("id", "1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void getNote() throws Exception {

        Note note = new Note();

        note.setPatientId(1);
        note.setNote("test note");
        LocalDate lt = LocalDate.now();
        note.setUpdateDate(lt);

        when(service.getNote(anyString())).thenReturn(java.util.Optional.of(note));

        this.mockMvc.perform(get("/patHistory/get")
                .param("id", String.valueOf(3))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note").value("test note"));

    }

    @Test
    void getNoteInvalid() throws Exception {

        this.mockMvc.perform(get("/patHistory/get")
                .param("id", String.valueOf(3))
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getNotes() throws Exception {
        Note note = new Note();
        note.setPatientId(1);
        note.setNote("test note");
        LocalDate lt = LocalDate.now();
        note.setUpdateDate(lt);
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        notes.add(note);
        notes.add(note);
        when(service.getNotes()).thenReturn(notes);

        this.mockMvc.perform(get("/patHistory/getAll")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    void getNotesNull() throws Exception {

        when(service.getNotes()).thenReturn(null);

        this.mockMvc.perform(get("/patient/getAll")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getNotesEmpty() throws Exception {

        when(service.getNotes()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/patient/getAll")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }


}