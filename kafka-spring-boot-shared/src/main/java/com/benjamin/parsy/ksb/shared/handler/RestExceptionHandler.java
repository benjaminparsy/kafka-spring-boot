package com.benjamin.parsy.ksb.shared.handler;

import com.benjamin.parsy.ksb.shared.exception.AbstractMessageException;
import com.benjamin.parsy.ksb.shared.exception.MessageException;
import com.benjamin.parsy.ksb.shared.exception.RestException;
import com.benjamin.parsy.ksb.shared.service.message.ErrorMessage;
import com.benjamin.parsy.ksb.shared.service.message.GlobalErrorCode;
import com.benjamin.parsy.ksb.shared.service.message.MessageService;
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

    @ExceptionHandler(value = {RestException.class, AbstractMessageException.class})
    public ResponseEntity<ErrorResponse> handleMessageException(MessageException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getCode(), ex.getDescription()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        try {

            List<FieldErrorRecord> fieldErrorRecordList = new LinkedList<>();
            for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                fieldErrorRecordList.add(new FieldErrorRecord(error.getField(), error.getDefaultMessage()));
            }

            ErrorMessage errorIE3 = messageService.getErrorMessage(GlobalErrorCode.INPUT_DATA_VALIDATION_ERROR);

            return ResponseEntity.badRequest().body(new ErrorResponse(errorIE3.getCode(),
                    errorIE3.getDescription(), Map.of("errors", fieldErrorRecordList)));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new ErrorResponse("[EG-2]",
                    "An error has occurred during input data validation."));
        }

    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        ErrorMessage errorMessage;
        try {
            errorMessage = messageService.getErrorMessage(GlobalErrorCode.UNKNOWN_ERROR_OCCURRED);
        } catch (Exception e) {
            errorMessage = new ErrorMessage("[EG-1]", "An unknown error has occurred.");
        }

        log.error(errorMessage.getFormattedMessage(), ex);

        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage.getCode(), errorMessage.getDescription()));
    }

}
