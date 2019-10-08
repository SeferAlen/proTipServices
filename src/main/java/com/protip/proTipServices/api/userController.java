package com.protip.proTipServices.api;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.model.Register;
import com.protip.proTipServices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class userController {

    @Autowired
    UserService userService;

    @GetMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> postUser(@Valid @RequestBody Register register) {
        userService.createUser(register.getProTipUser(), register.getPassword());

        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }
}
