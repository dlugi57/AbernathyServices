package com.abernathy.report.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

//@Data
@Getter
@Setter
@ToString
public class Patient {


    private Integer id;

    @NotNull(message = "firstName is mandatory")
    @NotBlank(message = "firstName is mandatory")
    @Size(max = 30, message = "First Name should be maximum 30 characters")
    String firstName;

    @NotNull(message = "lastName is mandatory")
    @NotBlank(message = "lastName is mandatory")
    @Size(max = 30, message = "First Name should be maximum 30 characters")
    String lastName;

    @JsonFormat(pattern="dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Birth date is mandatory")
    private LocalDate birthDate;

    @NotNull(message = "sex is mandatory")
    private PatientSex sex;

    @Size(max = 255, message = "Address should be maximum 255 characters")
    String address;

    @Size(max = 13, message = "phone should be maximum 13 characters")
    String phone;


}
