package com.protip.proTipServices.api;

import com.protip.proTipServices.model.Register;
import com.protip.proTipServices.service.UserService;
import com.protip.proTipServices.utility.UserCreateStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class userController {

    private final Logger logger = LoggerFactory.getLogger(userController.class);

    @Autowired
    UserService userService;

    @GetMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> postUser(@Valid @RequestBody Register register) {

        logger.info("User registering : " + register.getProTipUser().getFirstName() + " " + register.getProTipUser().getLastName());

        final UserCreateStatus userCreateStatus = userService.createUser(register.getProTipUser(), register.getPassword());
        if (userCreateStatus == UserCreateStatus.ALREADY_EXIST) {
            return new ResponseEntity<>("User email already exist", HttpStatus.BAD_REQUEST);
        } else if (userCreateStatus == UserCreateStatus.CREATED) {
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error while creating new user", HttpStatus.BAD_REQUEST);
        }
    }
}
