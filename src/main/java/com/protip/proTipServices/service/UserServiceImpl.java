package com.protip.proTipServices.service;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.repository.LoginRepository;
import com.protip.proTipServices.repository.UserRepository;
import jdk.jfr.internal.Logger;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void createUser(final ProTipUser proTipUser, final String password) {
        try {
            userRepository.save(proTipUser);
            Login login = new Login(proTipUser.getEmail(), passwordEncoder.encode(password), proTipUser);
            loginRepository.save(login);
        } catch (ConstraintViolationException e) {
        } catch (Exception e) {

        }
    }

    public List<ProTipUser> getUsers() {
        return userRepository.findAll();
    }
}
