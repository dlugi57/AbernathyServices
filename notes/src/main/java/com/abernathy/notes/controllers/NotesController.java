package com.abernathy.notes.controllers;

import com.abernathy.notes.model.Note;
import com.abernathy.notes.services.NotesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patHistory")
public class NotesController {
    static final Logger logger = LogManager
            .getLogger(NotesController.class);

    // Service initialization
    NotesService notesService;

    /**
     * Field injection of notes service
     *
     * @param notesService initialization of notes service
     */
    @Autowired
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    /**
     * Add note
     *
     * @param note note object
     * @return status and uri with new created patient
     */
    @PostMapping(value = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean addNote(@Valid @RequestBody Note note) {

        // if note already exist send status and error message
        if (!notesService.addNote(note)) {
            logger.error("POST note -> " +
                    "addNote /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/ Message : " +
                    " This note already exist");

            throw new ResponseStatusException(HttpStatus.CONFLICT, "This note already exist");
        }

        logger.info("POST note -> addNote /**/ HttpStatus : " + HttpStatus.CREATED + " /**/ " +
                "Result : '{}'.");

        return true;
    }

    /**
     * Update note
     *
     * @param note note object
     * @return status and uri with new created note
     */
    @PutMapping(value = "/update")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean updateNote(@Valid @RequestBody Note note) {

        // if user already exist send status and error message
        if (!notesService.updateNote(note)) {
            logger.error("PUT note -> " +
                    "updateNote /**/ HttpStatus : " + HttpStatus.NOT_FOUND + " /**/ Message : " +
                    " This note don't exist");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This note don't exist");
        }

        logger.info("POST note -> updateNote /**/ HttpStatus : " + HttpStatus.CREATED + " /**/ " +
                "Result : '{}'.");

        return true;
    }

    /**
     * Delete note
     *
     * @param id id of note
     */
    @DeleteMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteNote(@RequestParam String id) {
        // if there is no patient send status and error message
        if (!notesService.deleteNote(id)) {
            logger.error("DELETE note -> deleteNote /**/ Result : " + HttpStatus.NOT_FOUND
                    + " /**/ Message : This note don't exist : '{}'.", id.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This note don't exist");
        }
        logger.info("DELETE note -> deleteNote /**/ HttpStatus : " + HttpStatus.OK);
        return true;
    }

    /**
     * Get note
     *
     * @param id note id
     * @return note object
     */
    @GetMapping(value = "/get")
    @ResponseStatus(HttpStatus.OK)
    public Note getNote(@RequestParam() String id) {
        // get note
        Optional<Note> note = notesService.getNote(id);
        // if note don't exist send error message
        if (note.isEmpty()) {
            logger.error("GET note -> getNote /**/ HttpStatus : " + HttpStatus.NOT_FOUND + " " +
                    "/**/ Message :  note id " + id + " is wrong");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Wrong note: " + note);
        }

        logger.info("GET note -> getNote /**/ HttpStatus : " + HttpStatus.OK + " /**/ " +
                "Result : '{}'.", note.toString());
        return note.get();
    }

    /**
     * Get all notes
     *
     * @return List of notes
     */
    @GetMapping(value = "/getAll")
    public List<Note> getNotes() {
        List<Note> notes = notesService.getNotes();

        if (notes == null || notes.isEmpty()) {

            logger.error("GET notes -> getNotes /**/ Result : " + HttpStatus.NOT_FOUND + " /**/ " +
                    "Message : There is no notes in the data base");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no notes in the " +
                    "data base");
        }
        logger.info("GET notes -> getNotes /**/ HttpStatus : " + HttpStatus.OK + " /**/ Result : '{}'.", notes.toString());

        return notes;
    }

}
