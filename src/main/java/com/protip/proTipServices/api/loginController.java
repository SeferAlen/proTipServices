package com.protip.proTipServices.api;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.TokenSet;
import com.protip.proTipServices.service.UserService;
import com.protip.proTipServices.utility.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class loginController {

    @Autowired
    UserService userService;

    private final Logger logger = LoggerFactory.getLogger(userController.class);

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody Login login) {

        logger.info("User logging : " + login.getUsername());

        String token = JwtTokenUtil.generateToken(login);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping(value = "/token", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> setToken(@RequestBody TokenSet tokenSet) {

        logger.info("Token set for user : " + tokenSet.getUsername());

        userService.setToken(tokenSet.getToken(), tokenSet.getUsername());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
