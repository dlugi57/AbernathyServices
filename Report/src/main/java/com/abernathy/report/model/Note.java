package com.abernathy.report.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Note {

    private String id;

    private Integer patientId;

    private LocalDate updateDate;

    private String note;
}
