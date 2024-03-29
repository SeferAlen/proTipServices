package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.PasswordIncorrectException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.repository.LoginRepository;
import com.protip.proTipServices.utility.DbaUtil;
import com.protip.proTipServices.utility.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service for user authentication and authentication related actions
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String LOGIN_NULL = "Login must not be null";
    private static final String TOKEN_NULL = "Token must not be null";
    private static final String USER = "User ";
    private static final String PASSWORD = "Password ";
    private static final String NOT_EXIST = "does not exist";
    private static final String WRONG = "is wrong";
    private final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Method for generating token from login data
     *
     * @param login {@link Login} the login data
     * @return {@link String} the created token
     * @throws UserNotFoundException      the user not found exception
     * @throws PasswordIncorrectException the token expired exception
     */
    public String loginAndGenerateToken(final Login login) throws UserNotFoundException,
                                                                  PasswordIncorrectException {
        Objects.requireNonNull(login, LOGIN_NULL);

        logger.info("User logging : \nUsername: " + login.getUsername() + "\nPassword: " + login.getPassword());

        final Login user = loginRepository.findByUsername(login.getUsername());
        if (user == null) throw new UserNotFoundException(USER + login.getUsername() + NOT_EXIST);

        final boolean passwordCorrect = passwordEncoder.matches(login.getPassword(),
                loginRepository.findByUsername(login.getUsername()).getPassword());
        if (!passwordCorrect) throw new PasswordIncorrectException(PASSWORD + login.getPassword() + WRONG);

        user.setRole(loginRepository.findByUsername(login.getUsername()).getRole());

        return JwtTokenUtil.generateToken(user, getProTipUser(user));
    }

    /**
     * Method for updating token
     *
     * @param token {@link String} the old token
     * @return {@link String} the new created token
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    public String updateToken(final String token) {
        Objects.requireNonNull(token, TOKEN_NULL);

        final String username = JwtTokenUtil.getUsernameFromToken(token);
        final Login user = loginRepository.findByUsername(username);
        user.setRole(loginRepository.findByUsername(username).getRole());

        return JwtTokenUtil.generateToken(user, getProTipUser(user));
    }

    /**
     * Method for getting {@link ProTipUser} from token
     *
     * @param token {@link String} the token
     * @return {@link ProTipUser} the proTipUser
     */
    public ProTipUser getProTipUser(final String token) throws UserNotFoundException {
        Objects.requireNonNull(token, TOKEN_NULL);

        final Claims claims = JwtTokenUtil.getAllClaimsFromToken(token);
        final String username = claims.getSubject();
        final Login login = loginRepository.findByUsername(username);

        if (login == null) throw new UserNotFoundException(USER + username + NOT_EXIST);

        final ProTipUser proTipUser = DbaUtil.initializeAndUnproxy(login.getUser());

        return proTipUser;
    }

    /**
     * Method for getting {@link ProTipUser} from login data
     *
     * @param login {@link Login} the login data
     * @return {@link ProTipUser} the proTipUser
     */
    private ProTipUser getProTipUser(final Login login) {
        final ProTipUser proTipUser = DbaUtil.initializeAndUnproxy(login.getUser());

        return proTipUser;
    }
}
