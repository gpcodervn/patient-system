package com.gpcoder.exception.handler;

import com.gpcoder.model.response.ApiErrorResponse;
import com.gpcoder.model.response.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        log.error("handleMethodArgumentNotValid: ", ex);
        //
        ApiErrorResponse response = new ApiErrorResponse();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Validation Failed");
        response.setTrace(ex.getMessage());
        response.setPath(request.getContextPath());

        // Create ValidationError instances
        addFieldErrorsToResponse(response, ex.getBindingResult().getFieldErrors());
        addGlobalErrorsToResponse(response, ex.getBindingResult().getGlobalErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
                                                         final HttpStatus status, final WebRequest request) {
        log.error("handleBindException: ", ex);
        //
        ApiErrorResponse response = new ApiErrorResponse();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Binding Failed");
        response.setTrace(ex.getMessage());
        response.setPath(request.getContextPath());

        // Create ValidationError instances
        addFieldErrorsToResponse(response, ex.getBindingResult().getFieldErrors());
        addGlobalErrorsToResponse(response, ex.getBindingResult().getGlobalErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private void addFieldErrorsToResponse(ApiErrorResponse response, List<FieldError> fieldErrors) {
        for (FieldError fe : fieldErrors) {
            List<ValidationError> validationErrors = response.getErrors().computeIfAbsent(fe.getField(), k -> new ArrayList<>());
            ValidationError validationError = new ValidationError();
            validationError.setCode(fe.getCode());
            validationError.setMessage(messageSource.getMessage(fe, null));
            validationErrors.add(validationError);
        }
    }

    private void addGlobalErrorsToResponse(ApiErrorResponse response, List<ObjectError> globalErrors) {
        for (final ObjectError error : globalErrors) {
            List<ValidationError> validationErrors = response.getErrors().computeIfAbsent(error.getObjectName(), k -> new ArrayList<>());
            ValidationError validationError = new ValidationError();
            validationError.setCode(error.getCode());
            validationError.setMessage(messageSource.getMessage(error, null));
            validationErrors.add(validationError);
        }
    }

    // 422 : Constraint Violation
    @ExceptionHandler({ ConstraintViolationException.class })
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
                                                            final HttpServletRequest request) {
        log.error("handleConstraintViolation: ", ex);
        //
        ApiErrorResponse response = new ApiErrorResponse();
        response.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.setMessage("Constraint Violation");
        response.setTrace(ex.getMessage());
        response.setPath(request.getRequestURL().toString());

        // Create ValidationError instances
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            List<ValidationError> validationErrors = response.getErrors().computeIfAbsent(String.valueOf(violation.getPropertyPath()), k -> new ArrayList<>());
            ValidationError validationError = new ValidationError();
            validationError.setCode(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath());
            validationError.setMessage(violation.getMessage());
            validationErrors.add(validationError);
        }

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
