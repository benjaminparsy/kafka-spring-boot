package com.benjamin.parsy.ksb.order.application.rest.handler;

import com.benjamin.parsy.ksb.order.application.rest.exception.RestException;
import com.benjamin.parsy.ksb.order.application.rest.message.ErrorMessage;
import com.benjamin.parsy.ksb.order.application.rest.message.GlobalErrorCode;
import com.benjamin.parsy.ksb.order.application.rest.message.MessageService;
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

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ErrorResponse> handleMessageException(RestException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(ex.getErrorMessage().getCode(), ex.getErrorMessage().getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<FieldErrorRecord> fieldErrorRecordList = new LinkedList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrorRecordList.add(new FieldErrorRecord(error.getField(), error.getDefaultMessage()));
        }

        ErrorMessage errorMessage = messageService.getErrorMessage(GlobalErrorCode.INPUT_DATA_VALIDATION_ERROR.getCode());

        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage.getCode(),
                errorMessage.getMessage(), Map.of("errors", fieldErrorRecordList)));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        ErrorMessage errorMessage = messageService.getErrorMessage(GlobalErrorCode.UNKNOWN_ERROR_OCCURRED.getCode());
        log.error(errorMessage.getFormattedMessage(), ex);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(errorMessage.getCode(), errorMessage.getMessage()));
    }

}
