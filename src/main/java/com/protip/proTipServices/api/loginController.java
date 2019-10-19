package com.protip.proTipServices.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.TokenSet;
import com.protip.proTipServices.service.AuthenticationService;
import com.protip.proTipServices.service.UserService;
import com.protip.proTipServices.utility.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for accessing user login data in database
 */
@RestController
@RequestMapping("/login")
public class loginController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;

    private final Logger logger = LoggerFactory.getLogger(userController.class);

    /**
     * Login response entity.
     *
     * @param login the login
     * @return the response entity with body containing token and Http status
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody final Login login) {
        return new ResponseEntity<>(authenticationService.loginAndGenerateToken(login), HttpStatus.OK);
    }
}
