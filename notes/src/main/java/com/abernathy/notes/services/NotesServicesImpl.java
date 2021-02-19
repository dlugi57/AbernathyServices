package com.abernathy.notes.services;

import com.abernathy.notes.dao.NotesDao;
import com.abernathy.notes.model.Note;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service

public class NotesServicesImpl implements NotesService{

    static final Logger logger = LogManager
            .getLogger(NotesServicesImpl.class);

    NotesDao notesDao;

    /**
     * Field injection of notes dao
     *
     * @param notesDao notes dao
     */
    @Autowired
    public void setNotesDao(NotesDao notesDao) {
        this.notesDao = notesDao;
    }


    /**
     * Add note
     *
     * @param note note object
     * @return true when success
     */
    @Override
    public boolean addNote(Note note) {
        note.setUpdateDate(LocalDate.now());
        try {
            if (notesDao.save(note).getId() != null) {
                return true;
            }
        } catch (Exception e) {
            logger.info(e.toString());
        }

        return false;
    }

}
