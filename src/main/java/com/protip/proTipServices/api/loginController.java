package com.protip.proTipServices.api;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class loginController {

    @Autowired
    UserService userService;

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody Login login) {
        Login login1 = userService.findByUsername(login.getUsername());

        return new ResponseEntity<>(login.getUsername(), HttpStatus.OK);
    }
}
