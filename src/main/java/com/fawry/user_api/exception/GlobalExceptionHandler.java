package com.fawry.user_api.exception;

import static org.springframework.http.HttpStatus.*;

import com.fawry.user_api.dto.error.ErrorResponseDTO;
import com.fawry.user_api.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFoundException(EntityNotFoundException e) {
        Map<String, String> details = new LinkedHashMap<>();

        details.put("general", e.getMessage());
        return buildErrorResponse(ErrorCode.ENTITY_NOT_FOUND, e.getMessage(), NOT_FOUND,details);
    }


    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateResourceException(DuplicateResourceException e) {
        Map<String, String> details = new LinkedHashMap<>();

        details.put("general", e.getMessage());

        return buildErrorResponse(ErrorCode.DUPLICATE_RESOURCE, e.getMessage(), CONFLICT, details);
    }

    @ExceptionHandler(IllegalActionException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalActionException(IllegalActionException e) {

        Map<String, String> details = new LinkedHashMap<>();

        details.put("general", e.getMessage());
        return buildErrorResponse(ErrorCode.ILLEGAL_ACTION, e.getMessage(), BAD_REQUEST,details);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingRequestParameterException(MissingServletRequestParameterException e) {
        Map<String, String> details = new LinkedHashMap<>();
        details.put("name", e.getParameterName());
        details.put("type", e.getParameterType());
        details.put("general", e.getMessage());

        return buildErrorResponse(ErrorCode.MISSING_PARAMETER, "Missing request parameter", BAD_REQUEST, details);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        Map<String, String> details = new HashMap<>();
        details.put("requiredType", e.getRequiredType().getSimpleName());
        details.put("passedValue", e.getValue().toString());
        details.put("general", e.getMessage());
        return buildErrorResponse(ErrorCode.TYPE_MISMATCH, "Method argument type mismatch", BAD_REQUEST, details);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                details.put(error.getField(), error.getDefaultMessage())
        );
        details.put("general", ex.getMessage());
        return buildErrorResponse(ErrorCode.VALIDATION_ERROR, "Validation error", BAD_REQUEST, details);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(AccessDeniedException e) {
        Map<String, String> details = new HashMap<>();
        details.put("general", e.getMessage());
        return buildErrorResponse(ErrorCode.ACCESS_DENIED, e.getMessage(), FORBIDDEN,details);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(AuthenticationException e) {
        Map<String, String> details = new HashMap<>();
        details.put("general", e.getMessage());
        return buildErrorResponse(ErrorCode.UNAUTHORIZED, e.getMessage(), UNAUTHORIZED,details);
    }



    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(ErrorCode errorCode, String message, HttpStatus status) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ErrorResponseDTO errorResponse = ErrorResponseDTO.createErrorResponse(
                status,
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(ErrorCode errorCode, String message, HttpStatus status, Map<String, String> details) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .fieldErrors(details)
                .build();
        return ResponseEntity.status(status).body(errorResponse);
    }
}
