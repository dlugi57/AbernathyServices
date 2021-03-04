package com.abernathy.report.controllers;

import com.abernathy.report.model.Report;
import com.abernathy.report.services.ReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/assess")
public class ReportController {

    static final Logger logger = LogManager
            .getLogger(ReportController.class);

    // Service initialization
    ReportService reportService;

    /**
     * Field injection of report service
     *
     * @param reportService initialization of report service
     */
    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Get report by patient id
     *
     * @param patId patient id
     * @return report
     */
    @GetMapping(value = "/id")
    @ResponseStatus(HttpStatus.OK)
    public String getReportByPatientId(@RequestParam() Integer patId) {
        // get report
        Report report = reportService.calculateReportByPatientId(patId);
        // if patient don't exist send error message
        if (report == null) {
            logger.error("GET report -> getReportByPatientId /**/ HttpStatus : " + HttpStatus.NOT_FOUND + " " +
                    "/**/ Message :  patient id " + patId + " is wrong");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Wrong patient: " + patId);
        }

        logger.info("GET report -> getReportByPatientId /**/ HttpStatus : " + HttpStatus.OK + " " +
                "/**/ " +
                "Result : '{}'.", report.toString());
        return report.getMessage();
    }

    /**
     * Get report by patient last name
     *
     * @param familyName patient last name
     * @return report message
     */
    @GetMapping(value = "/familyName")
    @ResponseStatus(HttpStatus.OK)
    public String getReportByPatientLastname(@RequestParam() String familyName) {
        // get report
        Report report = reportService.calculateReportByPatientLastName(familyName);
        // if patient don't exist send error message
        if (report == null) {
            logger.error("GET report -> getReportByPatientLastname /**/ HttpStatus : " + HttpStatus.NOT_FOUND + " " +
                    "/**/ Message :  patient lastname " + familyName + " is wrong");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Wrong patient: " + familyName);
        }

        logger.info("GET report -> getReportByPatientLastname /**/ HttpStatus : " + HttpStatus.OK + " " +
                "/**/ " +
                "Result : '{}'.", report.toString());
        return report.getMessage();
    }

}
