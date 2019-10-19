package com.protip.proTipServices.service;

import com.protip.proTipServices.api.userController;
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
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public String loginAndGenerateToken(final Login login) {
        logger.info("User logging : " + login.getUsername());

        boolean passwordCorrect = passwordEncoder.matches(login.getPassword(), loginRepository.findByUsername(login.getUsername()).getPassword());
        logger.info("Password is : " + passwordCorrect);

        login.setRole(loginRepository.findByUsername(login.getUsername()).getRole());
        return JwtTokenUtil.generateToken(login);
    }
}
