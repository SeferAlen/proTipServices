package com.protip.proTipServices.exceptions;

import com.protip.proTipServices.api.userController;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    private final Logger logger = LoggerFactory.getLogger(userController.class);

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

    /**
     * Method for handling expired JWT exception
     *
     * @param ex {@link ExpiredJwtException} throwned exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<Object> handleExpiredJwtException(final ExpiredJwtException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Token expired", ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * Method for handling unsupported JWT exception
     *
     * @param ex {@link UnsupportedJwtException} throwned exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(UnsupportedJwtException.class)
    protected ResponseEntity<Object> handleUnsupportedJwtException(final UnsupportedJwtException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Unsupported token", ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * Method for handling malformed JWT exception
     *
     * @param ex {@link MalformedJwtException} throwned exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<Object> handleMalformedJwtException(final MalformedJwtException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Malformed token", ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * Method for handling signature JWT exception
     *
     * @param ex {@link SignatureException} throwned exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<Object> handleSignatureException(final SignatureException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Token signature not valid", ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * Method for handling illegal argument JWT exception
     *
     * @param ex {@link IllegalArgumentException} throwned exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Illegal token", ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * Method for handling expired jwt exception
     *
     * @param ex {@link TokenExpiredException} throwned exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(TokenExpiredException.class)
    protected ResponseEntity<Object> handleTokenExpiredException(final TokenExpiredException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Token expired", ex.getLocalizedMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Method for handling incorrect password exception
     *
     * @param ex {@link PasswordIncorrectException} throwned exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(PasswordIncorrectException.class)
    protected ResponseEntity<Object> handlePasswordIncorrectException(final PasswordIncorrectException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Password invalid", ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * Method for handling user not found exception
     *
     * @param ex {@link UserNotFoundException} throwned exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(final UserNotFoundException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        return new ResponseEntity<>(new ErrorDetails(new Date(), "User does not exist", ex.getLocalizedMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Method for handling various types of exceptions across application
     *
     * @param ex {@link GenericProTipServiceException} throwned exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(GenericProTipServiceException.class)
    protected ResponseEntity<Object> handleGenericProTipServiceException(final GenericProTipServiceException ex) {
        Objects.requireNonNull(ex, "Exception must not be null");

        logger.error("Unexpected exception or error" + ex.getLocalizedMessage());

        return new ResponseEntity<>(new ErrorDetails(new Date(), "Service error", "Please contact us with about this"),
                HttpStatus.BAD_REQUEST
        );
    }
}
