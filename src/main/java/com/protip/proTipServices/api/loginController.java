package com.protip.proTipServices.api;

import com.protip.proTipServices.model.Login;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class loginController {

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody Login login) {
        return new ResponseEntity<>(login.getUsername(), HttpStatus.OK);
    }
}
