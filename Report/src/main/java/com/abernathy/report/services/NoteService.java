package com.abernathy.report.services;

import com.abernathy.report.model.Note;

import java.util.List;

public interface NoteService {
    List<Note> getNotesByPatientId(Integer patientId);

    Note getNote(String id);

    boolean addNote(Note note);

    boolean updateNote(Note note);

    boolean deleteNote(String id);

    boolean deleteNotesByPatientId(Integer id);
}
