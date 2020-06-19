package com.gpcoder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "first_name", length = 64)
    private String firstName;

    @Column(name = "last_name", length = 64)
    private String lastName;

    @Column(name = "middle_name", length = 64)
    private String middleName;

    @Column(name = "patient_id", unique = true, nullable = false, length = 32)
    private String patientId;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender", length = 1)
    private String gender;

    @Column(name = "soft_deleted")
    private Boolean softDeleted;

}
