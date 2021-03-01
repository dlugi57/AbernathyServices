package com.abernathy.report.services;

import com.abernathy.report.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static com.abernathy.report.util.AgeCalculator.calculateAge;

@Service
public class ReportServiceImpl implements ReportService {

    static final Logger logger = LogManager
            .getLogger(ReportServiceImpl.class);

    private static Patient patient;

    private static List<Note> notes;

    private String[] triggerTerms = {"HEMOGLOBINE A1C", "MICROALBUMINE",
            "TAILLE", "POIDS", "FUME", "ANORMA", "CHOLESTEROL", "VERTIGE", "RECHUTE", "REACTION",
            "ANTICORPS"};

    @Autowired
    NoteService noteService;

    @Autowired
    PatientService patientService;

    ReportServiceImpl(NoteService noteService, PatientService patientService) {
        this.noteService = noteService;
        this.patientService = patientService;
    }

    public Report calculateReportByPatientId(Integer patientId) {

        List<Note> notes = noteService.getNotesByPatientId(patientId);
        Patient patient = patientService.getPatient(patientId);
        if (!notes.isEmpty() && patient != null) {
            return calculateReport(patient, notes);
        }

        return null;
    }

    public Report calculateReport(Patient patient, List<Note> notes) {

        if (patient == null && notes.isEmpty()) {
            return null;
        }

        int triggerTermsCount = countTriggerTerms(notes).get();

        return getReport(patient, triggerTermsCount);
    }

    private boolean isPatientMale(PatientSex sex) {
        if (sex == PatientSex.F) {
            return false;
        }
        return true;
    }


    private AtomicReference<Integer> countTriggerTerms(List<Note> notes) {
        // TODO: 01/03/2021 is that ok
        AtomicReference<Integer> countTerms = new AtomicReference<>(0);
        for (Note note : notes) {
            String clearNote = removeDiacriticalMarks(note.getNote().toUpperCase().trim());
            Stream.of(triggerTerms).forEach(triggerTerm -> {

                if (clearNote.contains(triggerTerm.toUpperCase().trim())) {
                    System.out.println(triggerTerm.toUpperCase().trim());
                    countTerms.getAndSet(countTerms.get() + 1);
                }

            });
        }
        return countTerms;
    }

    private static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    private String buildMessage(String firstName, String lastName, int age, ReportStatus status) {
        StringBuilder sb = new StringBuilder();
        sb.append("Patient: ");
        sb.append(firstName);
        sb.append(" ");
        sb.append(lastName);
        sb.append(" (age ");
        sb.append(age);
        sb.append(") diabetes assessment is: ");
        sb.append(status);
        String s = sb.toString();
        return s;
    }

    private Report getReport(Patient patient, int count) {

        boolean isMale = isPatientMale(patient.getSex());
        int age = calculateAge(patient.getBirthDate());
        ReportStatus status = ReportStatus.NONE;
        // borderline
        if ( age > 30 && (count >= 2 && count < 6)) {
            status = ReportStatus.BORDERLINE;

        }
        // danger
        else if ((isMale && age < 30 && (count == 3 || count == 4)) || (!isMale && age < 30 && (count >= 4 && count <= 6)) || (age > 30 && (count == 6 || count == 7))){
            status = ReportStatus.IN_DANGER;
        }
        // early onset
        else if ((isMale && age < 30 && count == 5)|| (!isMale && age < 30 && count >= 7) || (age > 30 && count >= 8) ){
            status = ReportStatus.EARLY_ONSET;
        }

        String message = buildMessage(patient.getFirstName(), patient.getLastName(),age,status);
        Report report = new Report();
        report.setMessage(message);
        report.setStatus(status);
        report.setTriggerTermsCount(count);

        return report;
    }
}
