package com.protip.proTipServices.service;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.repository.LoginRepository;
import com.protip.proTipServices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void createUser(final ProTipUser proTipUser) {
        userRepository.save(proTipUser);
        Login login = new Login(proTipUser.getFirstName()+proTipUser.getLastName(), passwordEncoder.encode("pass"),proTipUser);
        loginRepository.save(login);
    }

    public List<ProTipUser> getUsers() {
        return userRepository.findAll();
    }
}
