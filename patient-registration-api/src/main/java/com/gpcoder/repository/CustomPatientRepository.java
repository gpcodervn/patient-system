package com.gpcoder.repository;

import com.gpcoder.entity.Patient;
import com.gpcoder.model.request.PatientFilterable;
import org.springframework.data.domain.Page;

public interface CustomPatientRepository {

    Page<Patient> findAll(PatientFilterable filterable);
}
