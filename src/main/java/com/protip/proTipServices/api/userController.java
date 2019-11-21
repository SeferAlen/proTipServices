package com.protip.proTipServices.api;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.model.Register;
import com.protip.proTipServices.model.TokenSet;
import com.protip.proTipServices.repository.RoleRepository;
import com.protip.proTipServices.service.AuthorizationService;
import com.protip.proTipServices.service.UserService;
import com.protip.proTipServices.utility.UserCreateStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * REST controller for accessing users in database
 */
@RestController
@RequestMapping("/users")
public class userController extends basicController{
    private static final String INVALID_VALIDITY_DATE = "Invalid validity date";
    private static final String EMAIL_ALREADY_EXIST = "User email already exist";
    private static final String CREATED = "Created";
    private static final String CREATE_ERROR = "Error while creating new user";
    private static final UserCreateStatus USER_CREATED = UserCreateStatus.CREATED;
    private static final UserCreateStatus USER_ALREADY_EXIST = UserCreateStatus.ALREADY_EXIST;
    private static final UserCreateStatus USER_CREATED_FAILED = UserCreateStatus.FAILED;
    private final Logger logger = LoggerFactory.getLogger(userController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Get all users from database endpoint
     *
     * @param tokenSet {@link TokenSet} the tokenSet containing token
     * @return {@link ResponseEntity} the response entity with body containing users and Http status
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    @GetMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getUsers(@RequestBody final TokenSet tokenSet) throws GenericProTipServiceException,
                                                                                   TokenExpiredException {

        authorizationService.authorizeUser(tokenSet.getToken());

        return response(userService.getUsers(), HTTP_OK);
    }

    /**
     * Register new user endpoint
     *
     * @param register {@link Register} the register containing user info and password
     * @return {@link ResponseEntity} the response entity with body with message status and Http status
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> postUser(@Valid @RequestBody final Register register) {
        logger.info("User registering : " + register.getProTipUser().getFirstName() + " " + register.getProTipUser().getLastName());

        if (!checkDateValidity(register.getProTipUser().getProTipUserValidityDate()))
            return response(INVALID_VALIDITY_DATE, HTTP_BAD_REQUEST);

        final UserCreateStatus userCreateStatus = userService.createUser(register.getProTipUser(),
                                                                         register.getPassword(),
                                                                         roleRepository.findByName(ROLE_USER));
        if (userCreateStatus == USER_ALREADY_EXIST) {
            return response(EMAIL_ALREADY_EXIST, HTTP_BAD_REQUEST);
        } else if (userCreateStatus == USER_CREATED) {
            return response(CREATED, HTTP_CREATED);
        } else {
            return response(CREATE_ERROR, HTTP_BAD_REQUEST);
        }
    }

    /**
     * Check validity date sent by user trying to register in order to prevent date manipulation at frontend
     *
     * @param registerDate {@link Date} the registerDate sent from user
     * @return {@link boolean} the boolean representing validity status
     */
    private boolean checkDateValidity(final Date registerDate) {
        final Date now = new Date();
        final String today = new SimpleDateFormat(DATE_FORMAT).format(now);
        final String registerDateFormatted = new SimpleDateFormat(DATE_FORMAT).format(registerDate);

        if (today.equals(registerDateFormatted)) return true;
        else return false;
    }
}
