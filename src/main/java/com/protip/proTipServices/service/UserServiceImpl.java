package com.protip.proTipServices.service;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.repository.LoginRepository;
import com.protip.proTipServices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void createUser(final ProTipUser proTipUser, final String password) {
            userRepository.save(proTipUser);
            Login login = new Login(proTipUser.getEmail(), passwordEncoder.encode(password), proTipUser);
            loginRepository.save(login);
    }

    public List<ProTipUser> getUsers() {
        return userRepository.findAll();
    }

    public Login findByUsername(final String username) {
        return loginRepository.findByUsername(username);
    }
}
