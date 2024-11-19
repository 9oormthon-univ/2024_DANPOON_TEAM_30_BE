package soon.ready_action.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import soon.ready_action.global.exception.ReadyActionException;
import soon.ready_action.global.exception.dto.response.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReadyActionException.class)
    public ResponseEntity<ErrorResponse> expirationDateException(ReadyActionException e) {
        int status = e.getErrorCode().getStatus();

        ErrorResponse response = ErrorResponse.builder()
            .status(status)
            .message(e.getErrorCode().getMessage())
            .validation(e.getValidation())
            .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalidRequestHandler(MethodArgumentNotValidException e) {
        int status = e.getStatusCode().value();

        ErrorResponse response = ErrorResponse.builder()
            .status(status)
            .message("잘못된 요청입니다.")
            .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

}
