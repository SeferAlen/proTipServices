package com.protip.proTipServices.service;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.repository.LoginRepository;
import com.protip.proTipServices.repository.UserRepository;
import com.protip.proTipServices.utility.UserCreateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of UserService for dealing with user registration
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Db seeder.
     *
     * @param proTipUser for register
     * @param password of user
     * @return UserCreateStatus representing status of user creating process
     */
    public UserCreateStatus createUser(final ProTipUser proTipUser, final String password) {
        Objects.requireNonNull(proTipUser, "User cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");

            if(userRepository.findAll()
                    .stream()
                    .anyMatch(user -> user.getEmail().equals(proTipUser.getEmail()))) {
                return UserCreateStatus.ALREADY_EXIST;
            } else {
                userRepository.save(proTipUser);
                Login login = new Login(proTipUser.getEmail(), passwordEncoder.encode(password), proTipUser);
                loginRepository.save(login);
                return UserCreateStatus.CREATED;
            }
    }

    /**
     * Method for getting all users
     *
     * @return List<ProTipUser> list of all users
     */
    public List<ProTipUser> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Method for finding single user by username
     *
     * @param username for register
     * @return Login representing login of found user
     */
    public Login findByUsername(final String username) {
        return loginRepository.findByUsername(username);
    }
}
