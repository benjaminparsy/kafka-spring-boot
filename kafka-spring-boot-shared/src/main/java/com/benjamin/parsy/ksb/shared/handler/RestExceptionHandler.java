package com.benjamin.parsy.ksb.shared.handler;

import com.benjamin.parsy.ksb.shared.exception.ErrorCode;
import com.benjamin.parsy.ksb.shared.exception.ErrorMessage;
import com.benjamin.parsy.ksb.shared.exception.RestException;
import com.benjamin.parsy.ksb.shared.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    private final MessageService messageService;

    public RestExceptionHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler(value = RestException.class)
    public ResponseEntity<ErrorResponse> handleRestException(RestException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(ex.getCode(), ex.getDescription()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<FieldErrorRecord> fieldErrorRecordList = new LinkedList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrorRecordList.add(new FieldErrorRecord(error.getField(), error.getDefaultMessage()));
        }

        ErrorMessage errorIE3 = messageService.getErrorMessage(ErrorCode.BR8);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(errorIE3.getCode(), errorIE3.getDescription(), Map.of("errors", fieldErrorRecordList)));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        ErrorMessage errorIE3 = messageService.getErrorMessage(ErrorCode.BR5, ex.getMessage());

        log.error(errorIE3.getFormattedMessage(), ex);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(errorIE3.getCode(), errorIE3.getDescription()));
    }

}
