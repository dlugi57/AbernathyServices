package com.abernathy.notes.services;

import com.abernathy.notes.dao.NotesDao;
import com.abernathy.notes.model.Note;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class NotesServicesImplTest {

    @TestConfiguration
    static class NotesServicesImplTestContextConfiguration {
        @Bean
        public NotesService notesService() {
            return new NotesServicesImpl();
        }
    }

    @Resource
    NotesService notesService;

    @MockBean
    NotesDao notesDao;

    @Test
    void getPatients() {
        Note note = new Note();
        note.setPatientId(1);
        note.setNote("test note");
        LocalDate lt = LocalDate.now();
        note.setUpdateDate(lt);
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        notes.add(note);
        notes.add(note);

        // GIVEN
        given(notesDao.findAll()).willReturn(notes);
        // WHEN
        List<Note> patientsTest = notesService.getNotes();
        // THEN
        verify(notesDao, times(1)).findAll();
        assertThat(patientsTest.size()).isEqualTo(3);

    }

    @Test
    void getPatients_Invalid() {

        // GIVEN
        given(notesDao.findAll()).willReturn(Collections.emptyList());
        // WHEN
        List<Note> patientsTest = notesService.getNotes();
        // THEN
        verify(notesDao, times(1)).findAll();
        assertThat(patientsTest).isEqualTo(Collections.emptyList());

    }

    @Test
    void getPatient() {
        Note note = new Note();
        note.setPatientId(1);
        note.setNote("test note");
        LocalDate lt = LocalDate.now();
        note.setUpdateDate(lt);

        // GIVEN
        note.setId("abc");
        given(notesDao.findById(anyString())).willReturn(Optional.of(note));

        // WHEN
        Optional<Note> patientTest = notesService.getNote("abc");

        // THEN
        verify(notesDao, times(1)).findById(anyString());

        assertThat(patientTest).isEqualTo(Optional.of(note));
    }

    @Test
    void getPatient_Invalid() {

        // GIVEN
        given(notesDao.findById(anyString())).willReturn(null);

        // WHEN
        Optional<Note> patientTest = notesService.getNote("abc");

        // THEN
        verify(notesDao, times(1)).findById(anyString());

        assertThat(patientTest).isEqualTo(null);
    }

    @Test
    void addPatient() {
        Note note = new Note();
        note.setPatientId(1);
        note.setNote("test note");
        LocalDate lt = LocalDate.now();
        note.setUpdateDate(lt);
        // GIVEN
        note.setId("abc");
        given(notesDao.save(any(Note.class))).willReturn(note);
        // WHEN
        boolean userTest = notesService.addNote(note);
        // THEN
        verify(notesDao, times(1)).save(any(Note.class));

        assertThat(userTest).isEqualTo(true);
    }

    @Test
    void addPatient_Invalid() {
        Note note = new Note();
        note.setPatientId(1);
        note.setNote("test note");
        LocalDate lt = LocalDate.now();
        note.setUpdateDate(lt);
        // GIVEN
        note.setId("abc");
        given(notesDao.save(any(Note.class))).willReturn(null);
        // WHEN
        boolean patientTest = notesService.addNote(note);
        // THEN
        verify(notesDao, times(1)).save(any(Note.class));

        assertThat(patientTest).isEqualTo(false);
    }


    @Test
    void updatePatient() {
        Note note = new Note();
        note.setPatientId(1);
        note.setNote("test note");
        LocalDate lt = LocalDate.now();
        note.setUpdateDate(lt);
        // GIVENint
        given(notesDao.existsById(any())).willReturn(true);
        note.setId("abc");
        given(notesDao.save(any(Note.class))).willReturn(note);
        // WHEN
        boolean patientTest = notesService.updateNote(note);
        // THEN
        verify(notesDao, times(1)).existsById(anyString());
        verify(notesDao, times(1)).save(any(Note.class));

        assertThat(patientTest).isEqualTo(true);
    }

    @Test
    void updatePatient_Invalid() {
        Note note = new Note();
        note.setPatientId(1);
        note.setNote("test note");
        LocalDate lt = LocalDate.now();
        note.setUpdateDate(lt);
        // GIVENint
        given(notesDao.existsById(any())).willReturn(false);
        note.setId("abc");
        // WHEN
        boolean patientTest = notesService.updateNote(note);
        // THEN
        verify(notesDao, times(1)).existsById(anyString());

        assertThat(patientTest).isEqualTo(false);
    }

    @Test
    void deletePatient() {

        // GIVEN
        given(notesDao.existsById(anyString())).willReturn(true);

        // WHEN
        boolean patientTest = notesService.deleteNote("abc");
        // THEN
        verify(notesDao, times(1)).existsById(anyString());
        verify(notesDao, times(1)).deleteById(anyString());

        assertThat(patientTest).isEqualTo(true);
    }

    @Test
    void deletePatient_Invalid() {

        // GIVEN
        given(notesDao.existsById(anyString())).willReturn(false);

        // WHEN
        boolean patientTest = notesService.deleteNote("abc");
        // THEN
        verify(notesDao, times(1)).existsById(anyString());

        assertThat(patientTest).isEqualTo(false);
    }
}