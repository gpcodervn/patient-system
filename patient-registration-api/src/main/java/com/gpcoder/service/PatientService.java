package com.gpcoder.service;

import com.gpcoder.model.request.PatientFilterable;
import com.gpcoder.model.request.PatientRequest;
import com.gpcoder.model.response.PatientResponse;
import org.springframework.data.domain.Page;

/**
 * The service for handling the business logic of patient registration
 */
public interface PatientService {

    /**
     * Get list Patients based on the criteria
     *
     * @param filterable The criteria for getting patients
     * @return The list Patients
     */
    Page<PatientResponse> findAll(PatientFilterable filterable);

    /**
     * Get a Patient. Patients that have been soft-deleted should still be able to be loaded by this API.
     *
     * @param id The id attribute of the Patient entity
     * @return The Patient
     */
    PatientResponse findById(String id);

    /**
     * Save a Patient. If a record in the database exists with a conflicting PatientID but it’s been soft-deleted.
     * The record should be re-activated and the attributes updated to the values from the current request.
     *
     * @param request The Patient data will be stored
     * @return The saved Patient
     */
    PatientResponse save(PatientRequest request);

    /**
     * Update a Patient.
     *
     * @param id The id attribute of the Patient entity
     * @param request The Patient data will be stored
     * @return The updated Patient
     */
    PatientResponse update(String id, PatientRequest request);

    /**
     * Delete a Patient.
     *
     * @param id The id attribute of the Patient entity
     * @return true if deleted successfully, otherwise return false
     */
    boolean deleteById(String id);
}
