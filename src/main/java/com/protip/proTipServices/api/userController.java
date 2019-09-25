package com.protip.proTipServices.api;

import com.protip.proTipServices.model.Register;
import com.protip.proTipServices.repository.UserRepository;
import com.protip.proTipServices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.UUID;

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
    public ResponseEntity<?> postUser(@RequestBody Register register) {
        userService.createUser(register.getProTipUser(), register.getPassword());

        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }
}
