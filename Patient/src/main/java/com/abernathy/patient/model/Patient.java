package com.abernathy.patient.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.*;
import java.time.LocalDate;
@Data
@Entity
public class Patient {

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

    private LocalDate birthDate;

    @NotNull(message = "sex is mandatory")
    @NotBlank(message = "sex is mandatory")
    private PatientSex sex;

    @Column(length = 255)
    @Size(max = 255, message = "Address should be maximum 255 characters")
    String address;

    @Column(length = 13)
    @Size(max = 13, message = "phone should be maximum 13 characters")
    String phone;
}
