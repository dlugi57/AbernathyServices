package com.abernathy.notes.dao;

import com.abernathy.notes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotesDao  extends MongoRepository<Note, String> {
    List<Note> findAllByPatientId(Integer id);
}
