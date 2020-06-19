package com.gpcoder.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ApiErrorResponse {

    private int code;

    private String message;

    private Map<String, List<ValidationError>> errors = new HashMap<>();

    private String trace;

    private String path;

    private LocalDateTime timestamp = LocalDateTime.now();

}
