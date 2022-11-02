package com.gennadykulikov.peopledbweb.biz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message="The field cannot be empty")
    private String firstName;

    @NotEmpty(message="The field cannot be empty")
    private String lastName;

    @Past(message="Date of birth should be in the past")
    @NotNull(message="Date of birth must be specified")
    private LocalDate dob;

    @DecimalMin(value="1000", message="Salary must be at least 1000")
    @NotNull(message="The field cannot be empty")
    private BigDecimal salary;

    @Email(message="Provide a valid email address")
    @NotEmpty(message="The field cannot be empty")
    private String email;

    private String photoFile;

    public static Person parse(String csvLine) {
        String[] fields = csvLine.split(",\\s*");
        LocalDate dob = LocalDate.parse(fields[10], DateTimeFormatter.ofPattern("M/d/yyyy"));


        return new Person (null, fields[2], fields[4], dob,  new BigDecimal(fields[25]), fields[6],null);
    }
}
