package com.gpcoder.model.request;

import com.gpcoder.converter.DateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class PatientRequest {

    @NotBlank(message = "Please provide first name.")
    @Length( max = 64, message = "Maximum length of first name is 64 character")
    private String firstName;

    @NotBlank(message = "Please provide last name.")
    @Length( max = 64, message = "Maximum length of last name is 64 character")
    private String lastName;

    @Length( max = 64, message = "Maximum length of middle name is 64 character")
    private String middleName;

    @NotBlank(message = "Please provide patient id.")
    @Length( max = 32, message = "Maximum length of patient id is 32 character")
    private String patientId;

    @NotNull(message = "Please provide a valid date.")
    @JsonDeserialize(using = DateConverter.Deserialize.class)
    private Date dob;

    @Pattern(regexp = "^[MFO]$", message = "Only M or F or O are accepted.")
    @NotBlank
    private String gender;
}
