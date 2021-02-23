package com.abernathy.notes.services;

import com.abernathy.notes.dao.NotesDao;
import com.abernathy.notes.model.Note;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NotesServicesImpl implements NotesService {

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

    /**
     * Update note
     *
     * @param note note object
     * @return true when success
     */
    @Override
    public boolean updateNote(Note note) {
        note.setUpdateDate(LocalDate.now());
        if (notesDao.existsById(note.getId())) {

            return notesDao.save(note).getId() != null;

        }
        return false;
    }

    /**
     * Delete note
     *
     * @param id note
     * @return true when success
     */
    @Override
    public boolean deleteNote(String id) {
        if (notesDao.existsById(id)) {
            try {
                notesDao.deleteById(id);

                return true;

            } catch (Exception e) {
                logger.info(e.toString());
            }
        }
        return false;
    }

    /**
     * Get note
     *
     * @param id note id
     * @return note object
     */
    @Override
    public Optional<Note> getNote(String id) {
        return notesDao.findById(id);
    }

    /**
     * Get all notes
     *
     * @return list of notes
     */
    @Override
    public List<Note> getNotes() {
        return notesDao.findAll();
    }

    /**
     * Get notes by patient id
     *
     * @return list of notes
     */
    @Override
    public List<Note> getNotesByPatientId(Integer patientId) {
        return notesDao.findAllByPatientId(patientId);
    }

}
