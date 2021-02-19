package com.abernathy.notes.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Document(collection = "notes")
public class Note {
    @Id
    private String id;

    private Integer patientId;

    private LocalDate updateDate;

    private String note;
}
