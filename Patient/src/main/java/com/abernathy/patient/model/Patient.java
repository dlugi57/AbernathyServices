package com.abernathy.patient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

//@Data
@Getter
@Setter
@ToString
@Entity
public class Patient {

    //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    @NotNull(message = "firstName is mandatory")
    @NotBlank(message = "firstName is mandatory")
    @Size(max = 30, message = "First Name should be maximum 30 characters")
    String firstName;

    @Column(length = 30)
    @NotNull(message = "lastName is mandatory")
    @NotBlank(message = "lastName is mandatory")
    @Size(max = 30, message = "First Name should be maximum 30 characters")
    String lastName;

    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern="dd-MM-yyyy")
    @NotNull(message = "Birth date is mandatory")
    private LocalDate birthDate;

    @NotNull(message = "sex is mandatory")
    private PatientSex sex;

    @Column(length = 255)
    @Size(max = 255, message = "Address should be maximum 255 characters")
    String address;

    @Column(length = 13)
    @Size(max = 13, message = "phone should be maximum 13 characters")
    String phone;


}
