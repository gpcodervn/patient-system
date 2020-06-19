package com.gpcoder.repository;

import com.gpcoder.entity.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends BaseRepository<Patient, UUID>, CustomPatientRepository {

    @Query("select e from #{#entityName} e where e.patientId=?1")
    Optional<Patient> findByPatientId(String patientId);
}
