package com.abernathy.report.controllers;

import com.abernathy.report.model.Note;
import com.abernathy.report.model.Patient;
import com.abernathy.report.services.NoteService;
import com.abernathy.report.services.PatientNoteService;
import com.abernathy.report.services.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class NoteController {

    static final Logger logger = LogManager
            .getLogger(NoteController.class);

    // initialize objects
    NoteService noteService;
    PatientService patientService;


    /**
     * Field injection of patient note service
     *
     * @param noteService patient note service
     * @param patientService patient service
     */
    @Autowired
    public NoteController(NoteService noteService, PatientService patientService) {
        this.noteService = noteService;
        this.patientService = patientService;
    }

    /**
     * Show patient
     *
     * @param id    id of patient
     * @param model model of view
     * @return html page to show all infos
     */
    @GetMapping("/note/show/{id}")
    public String showPatient(@PathVariable("id") Integer id, Model model) {
        // get patient by Id and to model then show to the form
        List<Note> notes = noteService.getNotesByPatientId(id);
        Patient patient = patientService.getPatient(id);

        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);

        return "note/show";
    }



}
