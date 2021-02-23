package com.abernathy.notes.services;

import com.abernathy.notes.model.Note;

import java.util.List;
import java.util.Optional;

public interface NotesService {
    boolean addNote(Note note);

    boolean updateNote(Note note);

    boolean deleteNote(String id);

    Optional<Note> getNote(String id);

    List<Note> getNotes();

    List<Note> getNotesByPatientId(Integer patientId);
}
