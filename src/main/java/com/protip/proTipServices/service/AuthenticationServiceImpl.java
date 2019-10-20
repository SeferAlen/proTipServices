package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.PasswordIncorrectException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.repository.LoginRepository;
import com.protip.proTipServices.repository.RoleRepository;
import com.protip.proTipServices.repository.UserRepository;
import com.protip.proTipServices.utility.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    LoginRepository loginRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public String loginAndGenerateToken(final Login login) throws UserNotFoundException, PasswordIncorrectException {
        logger.info("User logging : \nUsername: " + login.getUsername() + "\nPassword: " + login.getPassword());

        final Login user = loginRepository.findByUsername(login.getUsername());
        if(user == null) throw new UserNotFoundException("User " + login.getUsername() + " does not exist");

        final boolean passwordCorrect = passwordEncoder.matches(login.getPassword(), loginRepository.findByUsername(login.getUsername()).getPassword());
        if(!passwordCorrect) throw new PasswordIncorrectException("Password " + login.getPassword() + " is wrong");
        login.setRole(loginRepository.findByUsername(login.getUsername()).getRole());

        return JwtTokenUtil.generateToken(login);
    }
}
