package com.protip.proTipServices.api;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.utility.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class loginController {

    private final Logger logger = LoggerFactory.getLogger(loginController.class);

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody Login login) {

        logger.info("User logging : " + login.getUsername());

        String token = JwtTokenUtil.generateToken(login);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
