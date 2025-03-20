package com.benjamin.parsy.runnetic.order.infrastructure.controller.rest;

import com.benjamin.parsy.runnetic.order.infrastructure.controller.rest.dto.ErrorCode;
import com.benjamin.parsy.runnetic.order.infrastructure.controller.rest.dto.ErrorResponse;
import com.benjamin.parsy.runnetic.order.infrastructure.controller.rest.dto.FieldErrorRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {

        List<FieldErrorRecord> fieldErrorRecordList = new LinkedList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrorRecordList.add(new FieldErrorRecord(error.getField(), error.getDefaultMessage()));
        }

        String code = ErrorCode.INPUT_DATA_VALIDATION_ERROR.getCode();
        String msg = messageSource.getMessage(code, new String[]{}, locale);

        return ResponseEntity.badRequest().body(new ErrorResponse(code, msg, Map.of("errors", fieldErrorRecordList)));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, Locale locale) {

        String code = ErrorCode.UNKNOWN_ERROR_OCCURRED.getCode();
        String msg = messageSource.getMessage(code, new String[]{}, locale);

        log.error(msg, ex);

        return ResponseEntity.badRequest().body(new ErrorResponse(code, msg));
    }

}
