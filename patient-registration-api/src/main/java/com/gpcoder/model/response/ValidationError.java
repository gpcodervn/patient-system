package com.gpcoder.model.response;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class ValidationError {

    private String code;

    @Nullable
    private String message;
}
