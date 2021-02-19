package com.abernathy.notes.dao;

import com.abernathy.notes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotesDao  extends MongoRepository<Note, String> {
}
