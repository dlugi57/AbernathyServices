package com.abernathy.report.model;

public enum ReportStatus {
    //NONE, BORDERLINE, IN_DANGER, EARLY_ONSET;

    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("In danger"),
    EARLY_ONSET("Early onset");

    private String text;

    ReportStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

}
