package com.protip.proTipServices.service;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.repository.LoginRepository;
import com.protip.proTipServices.repository.UserRepository;
import com.protip.proTipServices.utility.UserCreateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Service for users and user related actions
 */
@Service
public class UserServiceImpl implements UserService {
    private static final String USER_NULL = "Received message must not be null";
    private static final String PASSWORD_NULL = "Token must not be null";
    private static final String ROLE_NULL = "Received message must not be null";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Method for creating new user
     *
     * @param proTipUser {@link ProTipUser} the proTipUser to be created
     * @param password   {@link String}     the password
     * @param role       {@link Role}       the role
     * @return {@link UserCreateStatus}     the status representing user creation result
     */
    public UserCreateStatus createUser(final ProTipUser proTipUser, final String password, final Role role) {
        Objects.requireNonNull(proTipUser, USER_NULL);
        Objects.requireNonNull(password, PASSWORD_NULL);
        Objects.requireNonNull(role, ROLE_NULL);

        if (userRepository.findAll()
                .stream()
                .anyMatch(user -> user.getEmail().equals(proTipUser.getEmail()))) {
            return UserCreateStatus.ALREADY_EXIST;
        } else {
            userRepository.save(proTipUser);
            final Login login = new Login(proTipUser.getEmail(), passwordEncoder.encode(password), proTipUser, role);
            loginRepository.save(login);
            return UserCreateStatus.CREATED;
        }
    }

    /**
     * Method for getting all users
     *
     * @return {@link List<ProTipUser>} list of all users
     */
    public List<ProTipUser> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Method for finding single user by username
     *
     * @param username {@link String} the username
     * @return {@link Login} representing login of found user
     */
    public Login findByUsername(final String username) {
        return loginRepository.findByUsername(username);
    }
}
