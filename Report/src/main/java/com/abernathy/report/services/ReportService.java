package com.abernathy.report.services;

import com.abernathy.report.model.Note;
import com.abernathy.report.model.Patient;
import com.abernathy.report.model.Report;

import java.util.List;

public interface ReportService {

    Report calculateReportByPatientId(Integer patientId);
    Report calculateReport(Patient patient, List<Note> notes);
}
