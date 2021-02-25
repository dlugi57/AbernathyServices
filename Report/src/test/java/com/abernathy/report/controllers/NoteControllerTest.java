package com.abernathy.report.controllers;

import com.abernathy.report.model.Note;
import com.abernathy.report.model.Patient;
import com.abernathy.report.model.PatientSex;
import com.abernathy.report.services.NoteService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;
    @MockBean
    private PatientService patientService;

    static String firstname = "test";
    static String lastname = "test";
    static LocalDate birthdate = LocalDate.parse("17-03-2020", DateTimeFormatter.ofPattern("dd" +
            "-MM-yyyy"));
    static PatientSex sex = PatientSex.M;
    static String address = "test";
    static String phone = "test";
    static String note = "test";


    @Test
    void showPatient() throws Exception {

        List<Patient> patientList = new ArrayList<>();
        List<Note> noteList = new ArrayList<>();


        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);

        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);

        noteList.add(noteTest);


        patientList.add(patientTest);
        //WHEN
        when(patientService.getPatient(anyInt())).thenReturn(patientTest);
        when(noteService.getNotesByPatientId(anyInt())).thenReturn(noteList);


        //THEN
        mockMvc.perform(get("/note/show/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("note/show"));
    }

    @Test
    void showNote() throws Exception {
        List<Patient> patientList = new ArrayList<>();
        List<Note> noteList = new ArrayList<>();


        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);

        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);

        noteList.add(noteTest);


        patientList.add(patientTest);
        //WHEN
        when(patientService.getPatient(anyInt())).thenReturn(patientTest);
        when(noteService.getNote(anyString())).thenReturn(noteTest);

        //THEN
        mockMvc.perform(get("/note/details/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("note/details"));

    }

    @Test
    void showAddForm() throws Exception {
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

        // THEN
        mockMvc.perform(get("/note/add/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("note/add"));
    }

    @Test
    void validate() throws Exception {

        // WHEN
        when(noteService.addNote(any(Note.class))).thenReturn(true);

        //THEN
        mockMvc.perform(post("/note/validate")
                .param("patientId", String.valueOf(1))
                .param("note", note))
                .andDo(print())
                .andExpect(view().name("redirect:/note/show/1"))
                .andExpect(status().is3xxRedirection());
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

        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);
        //WHEN
        when(noteService.getNote(anyString())).thenReturn(noteTest);
        when(patientService.getPatient(anyInt())).thenReturn(patientTest);

        //THEN
        mockMvc.perform(get("/note/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("note/update"));
    }

    @Test
    void updateNote() throws Exception {

        when(noteService.addNote(any(Note.class))).thenReturn(true);

        //THEN
        mockMvc.perform(post("/note/update/1")
                .param("patientId", String.valueOf(1))
                .param("note", note))
                .andDo(print())
                .andExpect(view().name("redirect:/note/show/1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void deleteNote() throws Exception {

        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);
        noteTest.setId("60341a416bf78d08faabfbc3");
        when(noteService.getNote(anyString())).thenReturn(noteTest);

        when(noteService.deleteNote(anyString())).thenReturn(true);

        //THEN
        mockMvc.perform(get("/note/delete/60341a416bf78d08faabfbc3"))
                .andDo(print())
                .andExpect(view().name("redirect:/note/show/1"))
                .andExpect(status().is3xxRedirection());
        verify(noteService, times(1)).deleteNote(anyString());

    }
}