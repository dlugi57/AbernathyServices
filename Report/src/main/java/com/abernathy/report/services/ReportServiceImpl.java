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

    /**
     * List of trigger terms
     */
    private String[] triggerTerms = {"HEMOGLOBINE A1C", "MICROALBUMINE",
            "TAILLE", "POIDS", "FUME", "ANORMA", "CHOLESTEROL", "VERTIGE", "RECHUTE", "REACTION",
            "ANTICORPS"};

    @Autowired
    NoteService noteService;

    @Autowired
    PatientService patientService;

    /**
     * Calculate the report by user id
     *
     * @param patientId patient id
     * @return report object
     */
    public Report calculateReportByPatientId(Integer patientId) {

        List<Note> notes = noteService.getNotesByPatientId(patientId);
        Patient patient = patientService.getPatient(patientId);
        if ((notes != null && !notes.isEmpty()) && patient != null) {
            return calculateReport(patient, notes);
        }

        return null;
    }

    /**
     * Calculate the report by user id
     *
     * @param patientLastName patient id
     * @return report object
     */
    public Report calculateReportByPatientLastName(String patientLastName) {

        List<Patient> patients = patientService.getPatients();

        for (Patient patient : patients) {
            String cleanPatient =
                    removeDiacriticalMarks(patient.getLastName().toUpperCase().trim());
            String cleanComparePatient = removeDiacriticalMarks(patientLastName.toUpperCase().trim());

            if (cleanPatient.equals(cleanComparePatient) ) {
                List<Note> notes = noteService.getNotesByPatientId(patient.getId());

                if ((notes != null && !notes.isEmpty()) && patient != null) {
                    return calculateReport(patient, notes);
                }
            }
        }

        return null;
    }

    /**
     * Calculate report by patient and list of notes
     *
     * @param patient patient object
     * @param notes   notes list
     * @return report object
     */
    public Report calculateReport(Patient patient, List<Note> notes) {

        if (patient == null ||  notes == null) {
            return null;
        }
if (notes.isEmpty()){
    return null;
}

        int triggerTermsCount = countTriggerTerms(notes).get();

        return getReport(patient, triggerTermsCount);
    }

    /**
     * check if the patient is male
     *
     * @param sex sex of patient
     * @return true when male
     */
    public boolean isPatientMale(PatientSex sex) {
        if (sex == PatientSex.F) {
            return false;
        }
        return true;
    }

    /**
     * Count trigger terms
     *
     * @param notes list of notes
     * @return count of trigger terms
     */
    private AtomicReference<Integer> countTriggerTerms(List<Note> notes) {
        AtomicReference<Integer> countTerms = new AtomicReference<>(0);
        for (Note note : notes) {
            String clearNote = removeDiacriticalMarks(note.getNote().toUpperCase().trim());
            Stream.of(triggerTerms).forEach(triggerTerm -> {

                if (clearNote.contains(triggerTerm.toUpperCase().trim())) {
                    countTerms.getAndSet(countTerms.get() + 1);
                }

            });
        }
        return countTerms;
    }

    /**
     * Remove accents from string
     *
     * @param string note
     * @return clean string
     */
    private static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    /**
     * Build report message
     *
     * @param firstName first name
     * @param lastName  last name
     * @param age       age of patient
     * @param status    status of illness
     * @return string with message composed
     */
    private String buildMessage(String firstName, String lastName, int age, ReportStatus status) {
        StringBuilder sb = new StringBuilder();
        sb.append("Patient: ");
        sb.append(firstName);
        sb.append(" ");
        sb.append(lastName);
        sb.append(" (age ");
        sb.append(age);
        sb.append(") diabetes assessment is: ");
        sb.append(status.getText());
        String s = sb.toString();
        return s;
    }

    /**
     * Get status report by trigger terms
     *
     * @param patient patient object
     * @param count   count of trigger terms
     * @return report object
     */
    public Report getReport(Patient patient, int count) {

        boolean isMale = isPatientMale(patient.getSex());
        int age = calculateAge(patient.getBirthDate());
        ReportStatus status = ReportStatus.NONE;
        // borderline
        if (age > 30 && (count >= 2 && count < 6)) {
            status = ReportStatus.BORDERLINE;
        }
        // danger
        else if ((isMale && age < 30 && (count == 3 || count == 4)) || (!isMale && age < 30 && (count >= 4 && count <= 6)) || (age > 30 && (count == 6 || count == 7))) {
            status = ReportStatus.IN_DANGER;
        }
        // early onset
        else if ((isMale && age < 30 && count == 5) || (!isMale && age < 30 && count >= 7) || (age > 30 && count >= 8)) {
            status = ReportStatus.EARLY_ONSET;
        }

        String message = buildMessage(patient.getFirstName(), patient.getLastName(), age, status);
        Report report = new Report();
        report.setMessage(message);
        report.setStatus(status);
        report.setTriggerTermsCount(count);

        return report;
    }


}
