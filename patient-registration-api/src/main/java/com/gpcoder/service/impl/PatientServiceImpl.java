package com.gpcoder.service.impl;

import com.gpcoder.entity.Patient;
import com.gpcoder.exception.DuplicatedPatientException;
import com.gpcoder.exception.InvalidUUIDException;
import com.gpcoder.exception.RecordNotFoundException;
import com.gpcoder.model.request.PatientFilterable;
import com.gpcoder.model.request.PatientRequest;
import com.gpcoder.model.response.PatientResponse;
import com.gpcoder.repository.PatientRepository;
import com.gpcoder.service.PatientService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PatientResponse> findAll(PatientFilterable filterable) {
        return patientRepository.findAll(filterable).map(entity -> {
            PatientResponse response = new PatientResponse();
            BeanUtils.copyProperties(entity, response);
            return response;
        });
    }

    @Transactional(readOnly = true)
    @Override
    public PatientResponse findById(String id) {
        Patient patient = getPatient(id);
        return buildResponse(patient);
    }

    @Transactional
    @Override
    public PatientResponse save(PatientRequest request) {
        Optional<Patient> patient = patientRepository.findByPatientId(request.getPatientId());
        if (patient.isPresent() && BooleanUtils.isFalse(patient.get().getSoftDeleted())) {
            throw new DuplicatedPatientException("The patient with id [" + request.getPatientId() + "] already existed");
        }
        Patient newPatient = patient.orElse(new Patient());
        BeanUtils.copyProperties(request, newPatient);
        newPatient.setSoftDeleted(false);
        Patient savedPatient = patientRepository.save(newPatient);
        return buildResponse(savedPatient);
    }

    @Transactional
    @Override
    public PatientResponse update(String id, PatientRequest request) {
        Patient patient = getPatient(id);
        if(!StringUtils.equalsIgnoreCase(request.getPatientId(), patient.getPatientId()) &&
                patientRepository.findByPatientId(request.getPatientId()).isPresent()) {
            throw new DuplicatedPatientException("The patient with id [" + request.getPatientId() + "] already existed");
        }
        BeanUtils.copyProperties(request, patient);
        patient.setSoftDeleted(false);
        Patient savedPatient = patientRepository.save(patient);
        return buildResponse(savedPatient);
    }

    private UUID convert(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDException("The given UUUID is not valid");
        }
    }

    @Transactional
    @Override
    public boolean deleteById(String id) {
        Patient patient = getPatient(id);
        patientRepository.softDelete(patient);
        return true;
    }

    private Patient getPatient(String id) {
        UUID uuid = convert(id);
        return patientRepository.findById(uuid).orElseThrow(() ->
                new RecordNotFoundException("The patient could not be found with the given patient id [" + id + "]"));
    }

    private PatientResponse buildResponse(Patient patient) {
        PatientResponse response = new PatientResponse();
        BeanUtils.copyProperties(patient, response);
        return response;
    }
}
