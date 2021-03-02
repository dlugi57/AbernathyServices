package com.abernathy.report.services;

import com.abernathy.report.model.Note;
import com.abernathy.report.model.Patient;
import com.abernathy.report.model.PatientSex;
import com.abernathy.report.model.Report;

import java.util.List;

public interface ReportService {

    Report calculateReportByPatientId(Integer patientId);

    Report calculateReport(Patient patient, List<Note> notes);

    Report calculateReportByPatientLastName(String patientLastName);

    boolean isPatientMale(PatientSex sex);

    Report getReport(Patient patient, int count);

}
