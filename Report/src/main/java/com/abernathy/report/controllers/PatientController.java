package com.abernathy.report.controllers;

import com.abernathy.report.model.Patient;
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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
public class PatientController {

    static final Logger logger = LogManager
            .getLogger(PatientController.class);

    // initialize objects
    PatientService patientService;

    /**
     * Field injection of patient service
     *
     * @param patientService patient service
     */
    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    /**
     * Get all patients
     *
     * @param model model of view
     * @return List of patients
     */
    @RequestMapping("/patient/list")
    public String home(Model model) {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        // call depository find all bids to show to the view
        model.addAttribute("patients", patientService.getPatients());

        logger.info(("Is connected at "
                + format.format(calendar.getTime())));

        return "patient/list";
    }

    /**
     * Add patient form
     *
     * @param patient empty patient object
     * @return Empty form
     */
    @GetMapping("/patient/add")
    public String addBidForm(Patient patient) {
        return "patient/add";
    }

    /**
     * Add patient
     *
     * @param patient patient object
     * @param result  when validation goes wrong result
     * @param model   model of view
     * @return when success list of patient if not add form
     */
    @PostMapping("/patient/validate")
    public String validate(@Valid Patient patient, BindingResult result, Model model) {
        // check data valid and save to db, after saving return bid list
        if (!result.hasErrors()) {
            patientService.addPatient(patient);
            model.addAttribute("patients", patientService.getPatients());
            return "redirect:/patient/list";
        }
        return "patient/add";
    }

    /**
     * Update patient form
     *
     * @param id    id of patient to update
     * @param model model of view
     * @return form with patient to update
     */
    @GetMapping("/patient/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get patient by Id and to model then show to the form
        Patient patient = patientService.getPatient(id);

        model.addAttribute("patient", patient);

        return "patient/update";
    }

    /**
     * Update patient
     *
     * @param id      id of patient to update
     * @param patient patient object
     * @param result  when validation goes wrong result
     * @param model   model of view
     * @return when success list of patient if not update form
     */
    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable("id") Integer id, @Valid Patient patient,
                                BindingResult result, Model model) {
        //  check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            return "patient/update";
        }

        patient.setId(id);
        patientService.addPatient(patient);
        model.addAttribute("patients", patientService.getPatients());

        return "redirect:/patient/list";
    }

    /**
     * Delete patient
     *
     * @param id    id of patient to delete
     * @param model model of view
     * @return list of patient when success
     */
    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable("id") Integer id, Model model) {
        // Find patient by Id and delete the patient, return to patient list
        Patient patient = patientService.getPatient(id);
        patientService.deletePatient(patient.getId());
        model.addAttribute("patients", patientService.getPatients());
        return "redirect:/patient/list";
    }

}
