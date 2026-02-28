package br.com.tiagoschermack.family_tree.infrastructure.web.middleware;

import br.com.tiagoschermack.family_tree.dto.response.ErrorResponse;
import br.com.tiagoschermack.family_tree.dto.response.FieldErrorResponse;
import br.com.tiagoschermack.family_tree.shared.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@SuppressWarnings("unused")
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(exception.getStatus())
                .body  (new ErrorResponse(exception.getMessage(), request.getRequestURI(), null));
    }

    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<FieldErrorResponse> errorResponses = exception.getBindingResult()
                .getFieldErrors()
                .stream        ()
                .map           (ex -> new FieldErrorResponse(ex.getField(), ex.getDefaultMessage()))
                .toList        ();

        return ResponseEntity
                .badRequest()
                .body      (new ErrorResponse("Um ou mais campos são inválidos", request.getRequestURI(), errorResponses));
    }
}
