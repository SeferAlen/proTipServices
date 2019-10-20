package com.protip.proTipServices.api;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.model.TokenSet;
import com.protip.proTipServices.service.AuthenticationService;
import com.protip.proTipServices.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for various testing
 */
@RestController
@RequestMapping(value = "/test")
public class testController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    AuthorizationService authorizationService;

    /**
     * Test user authorization
     *
     * @param tokenSet the token set
     * @return the response entity
     */
    @GetMapping(value = "authUser")
    public ResponseEntity<?> testUserAuthorization(@RequestBody final TokenSet tokenSet) throws GenericProTipServiceException, TokenExpiredException {

        final Boolean userOk = authorizationService.authorizeUser(tokenSet.getToken());
        final String message;

        if(userOk) {
            message = "User is authorized";
        } else {
            message = "User not authorized, cannot proceed";
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Test admin authorization
     *
     * @param tokenSet the token set
     * @return the response entity
     */
    @GetMapping(value = "authAdmin")
    public ResponseEntity<?> testAdminAuthorization(@RequestBody final TokenSet tokenSet) throws GenericProTipServiceException, TokenExpiredException {
        final Boolean userOk = authorizationService.authorizeAdmin(tokenSet.getToken());

        final String message;
        if(userOk) {
            message = "User is authorized";
        } else {
            message = "User not authorized, cannot proceed";
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
