package com.protip.proTipServices.api;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.PasswordIncorrectException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.TokenSet;
import com.protip.proTipServices.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for accessing user login data in database
 */
@RestController
@RequestMapping("/login")
public class loginController extends basicController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Login endpoint for user login and token generation
     *
     * @param login {@link Login}     the login data
     * @return {@link ResponseEntity} the response entity with body containing token and Http status
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody final Login login) throws UserNotFoundException,
                                                                          PasswordIncorrectException,
                                                                          GenericProTipServiceException,
                                                                          TokenExpiredException {

        return new ResponseEntity<>(new TokenSet(authenticationService.loginAndGenerateToken(login)), HTTP_OK);
    }
}
