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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
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
     * Show patient with notes
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

    /**
     * Show details of one note
     *
     * @param id    id of note
     * @param model model of view
     * @return html page to show all infos
     */
    @GetMapping("/note/details/{id}")
    public String showNote(@PathVariable("id") String id, Model model) {
        // get note by Id and to model then show to the form
        Note note = noteService.getNote(id);
        Patient patient = patientService.getPatient(note.getPatientId());

        model.addAttribute("patient", patient);
        model.addAttribute("note", note);

        return "note/details";
    }

    /**
     * Add note form
     *
     * @param id    id of patient to add note
     * @param model model of view
     * @return form to add note
     */
    @GetMapping("/note/add/{id}")
    public String showAddForm(@PathVariable("id") Integer id, Model model, Note note) {
        // get patient by Id and to model then show to the form
        Patient patient = patientService.getPatient(id);

        model.addAttribute("patient", patient);

        return "note/add";
    }

    /**
     * Add note
     *
     * @param note note object
     * @param result  when validation goes wrong result
     * @param model   model of view
     * @return when success list of note if not add form
     */
    @PostMapping("/note/validate")
    public String validate(@Valid Note note, BindingResult result, Model model) {
        // check data valid and save to db, after saving return bid list
        if (!result.hasErrors()) {
            noteService.addNote(note);
           // model.addAttribute("notes", noteService.getNotesByPatientId(note.getPatientId()));
            return "redirect:/note/show/" + note.getPatientId();
        }
        // model.addAttribute("notes", noteService.getNotesByPatientId(note.getPatientId()));

        return "note/add";
    }

    /**
     * Update note form
     *
     * @param id    id of note to update
     * @param model model of view
     * @return form with note to update
     */
    @GetMapping("/note/update/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        // get note by Id and to model then show to the form
        Note note = noteService.getNote(id);

        model.addAttribute("note", note);
        Patient patient = patientService.getPatient(note.getPatientId());
        model.addAttribute("patient", patient);

        return "note/update";
    }

    /**
     * Update note
     *
     * @param id      id of note to update
     * @param note note object
     * @param result  when validation goes wrong result
     * @param model   model of view
     * @return when success list of notes if not update form
     */
    @PostMapping("/note/update/{id}")
    public String updatePatient(@PathVariable("id") String id, @Valid Note note,
                                BindingResult result, Model model) {
        //  check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            return "note/update";
        }

        note.setId(id);
        noteService.addNote(note);
        //model.addAttribute("patients", patientService.getPatients());

        return "redirect:/note/show/" + note.getPatientId();
    }

    /**
     * Delete note
     *
     * @param id    id of note to delete
     * @param model model of view
     * @return list of note when success
     */
    @GetMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable("id") String id, Model model) {
        // Find note by Id and delete the note, return to notes list
        Note note = noteService.getNote(id);
        noteService.deleteNote(note.getId());
        //model.addAttribute("patients", patientService.getPatients());
        return "redirect:/note/show/" + note.getPatientId();
    }

}
