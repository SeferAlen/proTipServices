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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

/**
 * REST controller for accessing users in database
 */
@RestController
@RequestMapping("/users")
public class userController {
    private final Logger logger = LoggerFactory.getLogger(userController.class);

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthorizationService authorizationService;

    /**
     * Gets all users from database.
     *
     * @param tokenSet containing token
     * @return the users
     */
    @GetMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getUsers(@RequestBody final TokenSet tokenSet) throws GenericProTipServiceException, TokenExpiredException {

        authorizationService.authorizeUser(tokenSet.getToken());

        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    /**
     * Register new user
     *
     * @param register containing user info and password
     * @return the response entity with body with message status and Http status
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> postUser(@Valid @RequestBody final Register register) {
        logger.info("User registering : " + register.getProTipUser().getFirstName() + " " + register.getProTipUser().getLastName());

        final UserCreateStatus userCreateStatus = userService.createUser(register.getProTipUser(),
                                                                         register.getPassword(),
                                                                         roleRepository.findByName("USER"));
        if (userCreateStatus == UserCreateStatus.ALREADY_EXIST) {
            return new ResponseEntity<>("User email already exist", HttpStatus.BAD_REQUEST);
        } else if (userCreateStatus == UserCreateStatus.CREATED) {
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error while creating new user", HttpStatus.BAD_REQUEST);
        }
    }
}
