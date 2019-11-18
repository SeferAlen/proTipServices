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

import javax.validation.constraints.Null;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

/**
 * Class for customized exceptions
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final HttpStatus HTTP_UNAUTHORIZED = HttpStatus.UNAUTHORIZED;
    private static final HttpStatus HTTP_BAD_REQUEST = HttpStatus.BAD_REQUEST;
    protected static final HttpStatus HTTP_INTERNAL_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String EXCEPTION_NULL = "Exception must not be null";
    private static final String EXCEPTION_UNEXPECTED = "Unexpected exception or error";
    private static final String VALIDATION_FAILED = "Validation failed";
    private static final String TOKEN_EXPIRED = "Token expired";
    private static final String TOKEN_UNSUPPORTED = "Unsupported token";
    private static final String TOKEN_MALFORMED = "Malformed token";
    private static final String TOKEN_SIGNATURE_INVALID = "Token signature not valid";
    private static final String TOKEN_ILLEGAL = "Illegal token";
    private static final String WRONG_PASSWORD = "Wrong password";
    private static final String USER_NOT_EXIST = "User does not exist";
    private static final String SERVICE_ERROR_MESSAGE = "Service error";
    private static final String SERVICE_ERROR_DETAILS = "Please contact us with about this";
    private final Logger logger = LoggerFactory.getLogger(userController.class);

    /**
     * Method for handling validation exception
     *
     * @param ex {@link MethodArgumentNotValidException} the thrown exception
     * @param headers {@link HttpHeaders}                the http headers
     * @param status {@link HttpStatus}                  the http status
     * @param request {@link WebRequest}                 the web request
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        Objects.requireNonNull(ex, EXCEPTION_NULL);

        return new ResponseEntity<>(new ErrorDetails(new Date(), VALIDATION_FAILED, ex.getBindingResult().toString()),
                                    HTTP_BAD_REQUEST
        );
    }

    /**
     * Method for handling expired JWT exception
     *
     * @param ex {@link ExpiredJwtException} the trowed exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<Object> handleExpiredJwtException(final ExpiredJwtException ex) {
        Objects.requireNonNull(ex, EXCEPTION_NULL);

        return new ResponseEntity<>(new ErrorDetails(new Date(), TOKEN_EXPIRED, ex.getLocalizedMessage()),
                HTTP_UNAUTHORIZED
        );
    }

    /**
     * Method for handling unsupported JWT exception
     *
     * @param ex {@link UnsupportedJwtException} the trowed exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(UnsupportedJwtException.class)
    protected ResponseEntity<Object> handleUnsupportedJwtException(final UnsupportedJwtException ex) {
        Objects.requireNonNull(ex, EXCEPTION_NULL);

        return new ResponseEntity<>(new ErrorDetails(new Date(), TOKEN_UNSUPPORTED, ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * Method for handling malformed JWT exception
     *
     * @param ex {@link MalformedJwtException} the trowed exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<Object> handleMalformedJwtException(final MalformedJwtException ex) {
        Objects.requireNonNull(ex, EXCEPTION_NULL);

        return new ResponseEntity<>(new ErrorDetails(new Date(), TOKEN_MALFORMED, ex.getLocalizedMessage()),
                HTTP_UNAUTHORIZED
        );
    }

    /**
     * Method for handling signature JWT exception
     *
     * @param ex {@link SignatureException} the trowed exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<Object> handleSignatureException(final SignatureException ex) {
        Objects.requireNonNull(ex, EXCEPTION_NULL);

        return new ResponseEntity<>(new ErrorDetails(new Date(), TOKEN_SIGNATURE_INVALID, ex.getLocalizedMessage()),
                HTTP_UNAUTHORIZED
        );
    }

    /**
     * Method for handling illegal argument JWT exception
     *
     * @param ex {@link IllegalArgumentException} the trowed exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex) {
        Objects.requireNonNull(ex, EXCEPTION_NULL);

        return new ResponseEntity<>(new ErrorDetails(new Date(), TOKEN_ILLEGAL, ex.getLocalizedMessage()),
                HTTP_UNAUTHORIZED
        );
    }

    /**
     * Method for handling expired jwt exception
     *
     * @param ex {@link TokenExpiredException} the trowed exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(TokenExpiredException.class)
    protected ResponseEntity<Object> handleTokenExpiredException(final TokenExpiredException ex) {
        Objects.requireNonNull(ex, EXCEPTION_NULL);

        return new ResponseEntity<>(new ErrorDetails(new Date(), TOKEN_EXPIRED, ex.getLocalizedMessage()),
                HTTP_BAD_REQUEST
        );
    }

    /**
     * Method for handling incorrect password exception
     *
     * @param ex {@link PasswordIncorrectException} the trowed exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(PasswordIncorrectException.class)
    protected ResponseEntity<Object> handlePasswordIncorrectException(final PasswordIncorrectException ex) {
        Objects.requireNonNull(ex, EXCEPTION_NULL);

        return new ResponseEntity<>(new ErrorDetails(new Date(), WRONG_PASSWORD, ex.getLocalizedMessage()),
                HTTP_UNAUTHORIZED
        );
    }

    /**
     * Method for handling user not found exception
     *
     * @param ex {@link UserNotFoundException} the trowed exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(final UserNotFoundException ex) {
        Objects.requireNonNull(ex, EXCEPTION_NULL);

        return new ResponseEntity<>(new ErrorDetails(new Date(), USER_NOT_EXIST, ex.getLocalizedMessage()),
                HTTP_BAD_REQUEST
        );
    }

    /**
     * Method for handling parsing exceptions
     *
     * @param ex {@link ParseException} the trowed exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(ParseException.class)
    protected ResponseEntity<Object> handleParseException(final ParseException ex) {
        Objects.requireNonNull(ex, EXCEPTION_NULL);

        return new ResponseEntity<>(new ErrorDetails(new Date(), SERVICE_ERROR_MESSAGE, SERVICE_ERROR_DETAILS),
                HTTP_INTERNAL_ERROR
        );
    }

    /**
     * Method for handling various types of exceptions across application
     *
     * @param ex {@link GenericProTipServiceException} the trowed exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(GenericProTipServiceException.class)
    protected ResponseEntity<Object> handleGenericProTipServiceException(final GenericProTipServiceException ex) {
        Objects.requireNonNull(ex, EXCEPTION_NULL);

        logger.error(EXCEPTION_UNEXPECTED + ex.getLocalizedMessage());

        return new ResponseEntity<>(new ErrorDetails(new Date(), SERVICE_ERROR_MESSAGE, SERVICE_ERROR_DETAILS),
                HTTP_INTERNAL_ERROR
        );
    }

    /**
     * Method for handling null pointer exception (even though it would be better this method is never used)
     *
     * @param ex {@link GenericProTipServiceException} the trowed exception
     * @return {@link ResponseEntity} with {@link ErrorDetails} as body and {@link HttpStatus} http status
     */
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(final NullPointerException ex) {
        Objects.requireNonNull(ex, EXCEPTION_NULL);

        logger.error(EXCEPTION_UNEXPECTED + ex.getLocalizedMessage());

        return new ResponseEntity<>(new ErrorDetails(new Date(), SERVICE_ERROR_MESSAGE, SERVICE_ERROR_DETAILS),
                HTTP_INTERNAL_ERROR
        );
    }
}
