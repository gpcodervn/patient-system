package com.gpcoder.model.response;

import com.gpcoder.converter.DateConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PatientResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String patientId;

    @JsonSerialize(using = DateConverter.Serialize.class)
    private Date dob;

    private String gender;

    private Boolean softDeleted;
}
