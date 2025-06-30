package com.AsadBabayev.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    public List<String> addValueMap(List<String> list, String newValue) {
        list.add(newValue);

        return list;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errorsMap = new HashMap<>();

        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) objectError).getField();
            String errorMessage = objectError.getDefaultMessage();

            if (errorsMap.containsKey(fieldName)) {
                errorsMap.put(fieldName, addValueMap(errorsMap.get(fieldName), errorMessage));
            } else {
                errorsMap.put(fieldName, addValueMap(new ArrayList<>(), errorMessage));
            }
        }

        return ResponseEntity.badRequest().body(createApiError(errorsMap));
    }

    private <T> ApiError createApiError(T errors) {
        ApiError<T> apiError = new ApiError<>();
        apiError.setId(UUID.randomUUID().toString());
        apiError.setErrorDate(new Date());
        apiError.setErrors(errors);

        return apiError;
    }
}
