package com.protip.proTipServices.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Objects;

/**
 * Class for customized exceptions
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Method for handling validation exception
     *
     * @param ex {@link MethodArgumentNotValidException} throwned exception
     * @param headers {@link HttpHeaders} http headers
     * @param status {@link HttpStatus} http status
     * @param request {@link WebRequest} web request
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Validation failed", ex.getBindingResult().toString()),
                                    HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<Object> handleExpiredJwtException(final ExpiredJwtException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Token expired", ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    protected ResponseEntity<Object> handleUnsupportedJwtException(final UnsupportedJwtException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Unsupported token", ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<Object> handleMalformedJwtException(final MalformedJwtException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Malformed token", ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<Object> handleSignatureException(final SignatureException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Token signature not valid", ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Illegal token", ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }
}
