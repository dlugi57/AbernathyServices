package com.abernathy.report.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Report {
    ReportStatus status;

    String message;

    int triggerTermsCount;
}
