package com.abernathy.report.services;

import com.abernathy.report.model.Note;
import com.abernathy.report.model.PatientSex;
import com.abernathy.report.proxies.NoteProxy;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class NoteServiceImplTest {

    @TestConfiguration
    static class NoteServiceImplTestTestContextConfiguration {
        @Bean
        public NoteService noteService() {
            return new NoteServiceImpl();
        }
    }

    @Resource
    NoteService noteService;

    @MockBean
    NoteProxy noteProxy;

    static String firstname = "test";
    static String lastname = "test";
    static LocalDate birthdate = LocalDate.parse("17-03-2020", DateTimeFormatter.ofPattern("dd" +
            "-MM-yyyy"));
    static PatientSex sex = PatientSex.M;
    static String address = "test";
    static String phone = "test";

    static String note = "test";

    @Test
    void getNotesByPatientId() {
        // GIVEN
        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);
        List<Note> noteList = new ArrayList<>();
        noteList.add(noteTest);
        given(noteProxy.getNotesByPatientId(anyInt())).willReturn(noteList);
        // WHEN
        List<Note> notes = noteService.getNotesByPatientId(1);
        // THEN
        verify(noteProxy, times(1)).getNotesByPatientId(anyInt());
        assertThat(notes).isEqualTo(noteList);
    }

    @Test
    void getNotesByPatientId_Invalid() {
        // GIVEN
        given(noteProxy.getNotesByPatientId(anyInt())).willReturn(Collections.emptyList());
        // WHEN
        List<Note> notes = noteService.getNotesByPatientId(1);
        // THEN
        verify(noteProxy, times(1)).getNotesByPatientId(anyInt());
        assertThat(notes).isEqualTo(Collections.emptyList());
    }

    @Test
    void getNote() {
        // GIVEN
        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);

        given(noteProxy.getNote(anyString())).willReturn(noteTest);
        // WHEN
        Note note = noteService.getNote("60341a416bf78d08faabfbc3");
        // THEN
        verify(noteProxy, times(1)).getNote(anyString());
        assertThat(note).isEqualTo(noteTest);
    }

    @Test
    void addNote() {
        // GIVEN
        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);
        given(noteProxy.addNote(any(Note.class))).willReturn(true);
        // WHEN
        boolean note = noteService.addNote(noteTest);
        // THEN
        verify(noteProxy, times(1)).addNote(any(Note.class));

        assertThat(note).isEqualTo(true);
    }

    @Test
    void addNote_Invalid() {
        // GIVEN
        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);
        given(noteProxy.addNote(any(Note.class))).willReturn(false);
        // WHEN
        boolean note = noteService.addNote(noteTest);
        // THEN
        verify(noteProxy, times(1)).addNote(any(Note.class));

        assertThat(note).isEqualTo(false);
    }


    @Test
    void updateNote() {

        // GIVEN
        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);
        noteTest.setId("60341a416bf78d08faabfbc3");
        given(noteProxy.updateNote(any(Note.class))).willReturn(true);
        // WHEN
        boolean note = noteService.updateNote(noteTest);
        // THEN
        verify(noteProxy, times(1)).updateNote(any(Note.class));

        assertThat(note).isEqualTo(true);
    }

    @Test
    void updateNote_Invalid() {

        // GIVEN
        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);
        noteTest.setId("60341a416bf78d08faabfbc3");
        given(noteProxy.updateNote(any(Note.class))).willReturn(false);
        // WHEN
        boolean note = noteService.updateNote(noteTest);
        // THEN
        verify(noteProxy, times(1)).updateNote(any(Note.class));

        assertThat(note).isEqualTo(false);
    }

    @Test
    void deleteNote() {
        // GIVEN
        given(noteProxy.deleteNote(anyString())).willReturn(true);
        // WHEN
        boolean note = noteService.deleteNote("60341a416bf78d08faabfbc3");
        // THEN
        verify(noteProxy, times(1)).deleteNote(anyString());

        assertThat(note).isEqualTo(true);
    }

    @Test
    void deleteNote_invalid() {
        // GIVEN
        given(noteProxy.deleteNote(anyString())).willReturn(false);
        // WHEN
        boolean note = noteService.deleteNote("60341a416bf78d08faabfbc3");
        // THEN
        verify(noteProxy, times(1)).deleteNote(anyString());

        assertThat(note).isEqualTo(false);
    }

    @Test
    void deleteNotesByPatientId() {
        // GIVEN
        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);
        noteTest.setId("60341a416bf78d08faabfbc3");
        List<Note> noteList = new ArrayList<>();
        noteList.add(noteTest);
        given(noteProxy.deleteNote(anyString())).willReturn(true);
        given(noteProxy.getNotesByPatientId(anyInt())).willReturn(noteList);

        // WHEN
        boolean note = noteService.deleteNotesByPatientId(1);
        // THEN
        verify(noteProxy, times(1)).getNotesByPatientId(anyInt());

        verify(noteProxy, times(1)).deleteNote(anyString());

        assertThat(note).isEqualTo(true);
    }

    @Test
    void deleteNotesByPatientId_Invalid() {
        // GIVEN
        given(noteProxy.getNotesByPatientId(anyInt())).willReturn(Collections.emptyList());

        // WHEN
        boolean note = noteService.deleteNotesByPatientId(1);
        // THEN
        verify(noteProxy, times(1)).getNotesByPatientId(anyInt());


        assertThat(note).isEqualTo(false);
    }
}