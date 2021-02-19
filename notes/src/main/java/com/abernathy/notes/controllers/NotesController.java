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
}
