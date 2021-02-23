package com.abernathy.report.services;

import com.abernathy.report.model.Note;
import com.abernathy.report.proxies.NoteProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoteServiceImpl implements NoteService{

    static final Logger logger = LogManager
            .getLogger(NoteServiceImpl.class);

    @Autowired
    NoteProxy noteProxy;

    /**
     * Get all notes by patient id
     *
     * @param patientId id of patient
     * @return list of patients notes
     */
    @Override
    public List<Note> getNotesByPatientId(Integer patientId) {
        return noteProxy.getNotesByPatientId(patientId);
    }

    /**
     * Get note
     *
     * @param id note id
     * @return note object
     */
    @Override
    public Note getNote(String id) {
        return noteProxy.getNote(id);
    }

    /**
     * Add note
     *
     * @param note note object
     * @return true when success
     */
    @Override
    public boolean addNote(Note note) {
        if (noteProxy.addNote(note)) {
            return true;

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
        if (noteProxy.updateNote(note)) {

            return true;

        }
        return false;
    }

    /**
     * Delete note
     *
     * @param id note object
     * @return true when success
     */
    @Override
    public boolean deleteNote(String id) {
        if (noteProxy.deleteNote(id)) {
            return true;
        }
        return false;
    }
}
