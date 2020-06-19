package com.gpcoder.controller;

import com.gpcoder.model.request.PatientFilterable;
import com.gpcoder.model.request.PatientRequest;
import com.gpcoder.model.response.PatientResponse;
import com.gpcoder.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/patients")
@Slf4j
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public Page<PatientResponse> findAll(@Valid PatientFilterable filterable) {
        log.info("findAll " + filterable);
        return patientService.findAll(filterable);
    }

    @GetMapping("/{patientId}")
    public PatientResponse findById(@PathVariable String patientId) {
        return patientService.findById(patientId);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PatientResponse create(@Valid @RequestBody PatientRequest request) {
        return patientService.save(request);
    }

    @PutMapping("/{patientId}")
    public PatientResponse update(@PathVariable String patientId, @Valid @RequestBody PatientRequest request) {
        return patientService.update(patientId, request);
    }

    @DeleteMapping("/{patientId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String patientId) {
        patientService.deleteById(patientId);
    }
}
