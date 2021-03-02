package com.abernathy.report.controllers;

import com.abernathy.report.model.Report;
import com.abernathy.report.model.ReportStatus;
import com.abernathy.report.services.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    void getReportByPatientId() throws Exception {

        Report report = new Report();
        report.setStatus(ReportStatus.EARLY_ONSET);
        report.setMessage("test");
        report.setTriggerTermsCount(3);

        when(reportService.calculateReportByPatientId(anyInt())).thenReturn(report);

        this.mockMvc.perform(get("/assess/id")
                .param("patId", String.valueOf(3))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("test"));
    }

    @Test
    void getReportByPatientId_Invalid() throws Exception {

        when(reportService.calculateReportByPatientId(anyInt())).thenReturn(null);

        this.mockMvc.perform(get("/assess/id")
                .param("patId", String.valueOf(3))
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getReportByPatientLastname() throws Exception {
        Report report = new Report();
        report.setStatus(ReportStatus.EARLY_ONSET);
        report.setMessage("test");
        report.setTriggerTermsCount(3);

        when(reportService.calculateReportByPatientLastName(anyString())).thenReturn(report);

        this.mockMvc.perform(get("/assess/familyName")
                .param("familyName", "test")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("test"));
    }

    @Test
    void getReportByPatientLastname_Invalid() throws Exception {

        when(reportService.calculateReportByPatientLastName(anyString())).thenReturn(null);

        this.mockMvc.perform(get("/assess/familyName")
                .param("familyName", "test")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}