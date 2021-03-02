package com.abernathy.report.services;

import com.abernathy.report.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ReportServiceImplTest {

    @TestConfiguration
    static class ReportServiceImplTestContextConfiguration {
        @Bean
        public ReportService reportService() {
            return new ReportServiceImpl();
        }
    }

    @Resource
    ReportService reportService;

    @MockBean
    NoteService noteService;

    @MockBean
    PatientService patientService;

    static String firstname = "test";
    static String lastname = "test";
    static LocalDate birthdate = LocalDate.parse("17-03-2020", DateTimeFormatter.ofPattern("dd" +
            "-MM-yyyy"));
    static PatientSex sex = PatientSex.M;
    static String address = "test";
    static String phone = "test";

    @Test
    void calculateReportByPatientId() {
        List<Patient> patientList = new ArrayList<>();

        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);

        patientList.add(patientTest);

        // GIVEN
        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);
        List<Note> noteList = new ArrayList<>();
        noteList.add(noteTest);

        Report report = new Report();
        report.setStatus(ReportStatus.NONE);
        report.setMessage("Patient: test test (age 0) diabetes assessment is: None");
        report.setTriggerTermsCount(0);

        // WHEN
        when(patientService.getPatient(anyInt())).thenReturn(patientTest);
        when(noteService.getNotesByPatientId(anyInt())).thenReturn(noteList);

        // WHEN
        Report reportTest = reportService.calculateReportByPatientId(1);

        // THEN
        verify(patientService, times(1)).getPatient(anyInt());
        verify(noteService, times(1)).getNotesByPatientId(anyInt());

        // THEN
        assertThat(report.getMessage()).isEqualTo(reportTest.getMessage());

    }

    @Test
    void calculateReportByPatientId_Invalid() {
        List<Patient> patientList = new ArrayList<>();

        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);

        patientList.add(patientTest);

        // WHEN
        when(patientService.getPatient(anyInt())).thenReturn(patientTest);
        when(noteService.getNotesByPatientId(anyInt())).thenReturn(Collections.emptyList());

        // WHEN
        Report reportTest = reportService.calculateReportByPatientId(1);

        // THEN
        verify(patientService, times(1)).getPatient(anyInt());
        verify(noteService, times(1)).getNotesByPatientId(anyInt());

        // THEN
        assertThat(reportTest).isEqualTo(null);

    }

    @Test
    void calculateReportByPatientLastName() {
        List<Patient> patientList = new ArrayList<>();

        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);

        patientList.add(patientTest);

        // GIVEN
        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);
        List<Note> noteList = new ArrayList<>();
        noteList.add(noteTest);

        Report report = new Report();
        report.setStatus(ReportStatus.NONE);
        report.setMessage("Patient: test test (age 0) diabetes assessment is: None");
        report.setTriggerTermsCount(0);

        // WHEN
        when(patientService.getPatients()).thenReturn(patientList);
        when(noteService.getNotesByPatientId(anyInt())).thenReturn(noteList);

        // WHEN
        Report reportTest = reportService.calculateReportByPatientLastName("test");

        // THEN
        verify(patientService, times(1)).getPatients();
        verify(noteService, times(1)).getNotesByPatientId(anyInt());

        // THEN
        assertThat(report.getMessage()).isEqualTo(reportTest.getMessage());
    }

    @Test
    void calculateReportByPatientLastName_Invalid() {
        List<Patient> patientList = new ArrayList<>();

        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);

        patientList.add(patientTest);

        // WHEN
        when(patientService.getPatients()).thenReturn(patientList);
        when(noteService.getNotesByPatientId(anyInt())).thenReturn(Collections.emptyList());

        // WHEN
        Report reportTest = reportService.calculateReportByPatientLastName("test");

        // THEN
        verify(patientService, times(1)).getPatients();
        verify(noteService, times(1)).getNotesByPatientId(anyInt());

        // THEN
        assertThat(reportTest).isEqualTo(null);
    }

    @Test
    void calculateReport() {
        List<Patient> patientList = new ArrayList<>();

        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        patientTest.setBirthDate(birthdate);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);

        patientList.add(patientTest);

        // GIVEN
        Note noteTest = new Note();
        noteTest.setNote("test");
        noteTest.setPatientId(1);
        List<Note> noteList = new ArrayList<>();
        noteList.add(noteTest);

        Report report = new Report();
        report.setStatus(ReportStatus.NONE);
        report.setMessage("Patient: test test (age 0) diabetes assessment is: None");
        report.setTriggerTermsCount(0);

        // WHEN
        Report reportTest = reportService.calculateReport(patientTest, noteList);

        // THEN
        assertThat(report.getMessage()).isEqualTo(reportTest.getMessage());
    }

    @Test
    void calculateReport_Invalid() {
        List<Patient> patientList = new ArrayList<>();

        //GIVEN
        // WHEN
        Report reportTest = reportService.calculateReport(null, Collections.emptyList());

        // THEN
        assertThat(reportTest).isEqualTo(null);
    }

    @Test
    void isPatientMale() {
        boolean testSex = reportService.isPatientMale(sex);

        // THEN
        assertThat(testSex).isEqualTo(true);

    }

    @Test
    void isPatientMale_Invalid() {
        boolean testSex = reportService.isPatientMale(PatientSex.F);

        // THEN
        assertThat(testSex).isEqualTo(false);

    }

    @Test
    void getReport_age30_triggerTermsCount3() {

        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        LocalDate birthdateTest = LocalDate.parse("17-03-1988", DateTimeFormatter.ofPattern(
                "dd" +
                        "-MM-yyyy"));
        patientTest.setBirthDate(birthdateTest);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);


        Report testReport = reportService.getReport(patientTest, 3);

        // THEN
        assertThat(testReport.getStatus()).isEqualTo(ReportStatus.BORDERLINE);
    }

    @Test
    void getReport_age70_triggerTermsCount6() {

        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        LocalDate birthdateTest = LocalDate.parse("17-03-1950", DateTimeFormatter.ofPattern(
                "dd" +
                        "-MM-yyyy"));
        patientTest.setBirthDate(birthdateTest);
        patientTest.setSex(sex);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);


        Report testReport = reportService.getReport(patientTest, 6);

        // THEN
        assertThat(testReport.getStatus()).isEqualTo(ReportStatus.IN_DANGER);
    }

    @Test
    void getReport_age70_triggerTermsCount9() {

        //GIVEN
        Patient patientTest = new Patient();
        patientTest.setId(1);
        patientTest.setFirstName(firstname);
        patientTest.setLastName(lastname);
        LocalDate birthdateTest = LocalDate.parse("17-03-1950", DateTimeFormatter.ofPattern(
                "dd" +
                        "-MM-yyyy"));
        patientTest.setBirthDate(birthdateTest);
        patientTest.setSex(PatientSex.F);
        patientTest.setPhone(phone);
        patientTest.setAddress(address);

        Report testReport = reportService.getReport(patientTest, 9);

        // THEN
        assertThat(testReport.getStatus()).isEqualTo(ReportStatus.EARLY_ONSET);
    }
}