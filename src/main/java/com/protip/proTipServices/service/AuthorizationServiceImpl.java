package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.repository.LoginRepository;
import com.protip.proTipServices.repository.RoleRepository;
import com.protip.proTipServices.repository.UserRepository;
import com.protip.proTipServices.utility.DbaUtil;
import com.protip.proTipServices.utility.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * Service for user authorization and authorization related actions
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";
    private static final String TOKEN_NULL = "Token must not be null";
    private static final String CLAIM_ROLE = "Role";
    private static final String SOMETHING_WRONG = "Something wrong about roles or token";

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Method for user authorization
     *
     * @param token {@link String} the token
     * @return {@link boolean}     the boolean representing authorization result
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    public boolean authorizeUser(final String token) throws GenericProTipServiceException, TokenExpiredException {
        Objects.requireNonNull(token, TOKEN_NULL);

        final Role role = basicAuthorization(token);

        if (roleRepository.findByName(ROLE_USER) == role || roleRepository.findByName(ROLE_ADMIN) == role) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for admin authorization
     *
     * @param token {@link String} the token
     * @return {@link boolean}     the boolean representing authorization result
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    public boolean authorizeAdmin(final String token) throws GenericProTipServiceException, TokenExpiredException {
        Objects.requireNonNull(token, TOKEN_NULL);

        final Role role = basicAuthorization(token);

        if (roleRepository.findByName(ROLE_ADMIN) == role) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for checking validity of proTipUserValidity field in token
     *
     * @param token {@link String} the token
     * @return {@link boolean}     the boolean representing validity status
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    public boolean checkProTipUserValidity(final String token) throws GenericProTipServiceException,
                                                                      TokenExpiredException {
        Objects.requireNonNull(token, TOKEN_NULL);

        final Claims claims = JwtTokenUtil.getAllClaimsFromToken(token);
        final Login login = loginRepository.findByUsername(claims.getSubject());
        final ProTipUser proTipUser = DbaUtil.initializeAndUnproxy(login.getUser());

        if (proTipUser.getProTipUserValidityDate() == null ||
                proTipUser.getProTipUserValidityDate().before(new Date())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Public method for getting user role from token
     *
     * @param token {@link String} the token
     * @return {@link Role}        the role
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    public Role getRole(final String token) throws GenericProTipServiceException, TokenExpiredException {
        Objects.requireNonNull(token, TOKEN_NULL);

        return basicAuthorization(token);
    }

    /**
     * Method for getting user role from token
     *
     * @param token {@link String} the token
     * @return {@link Role}        the role
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    private Role basicAuthorization(final String token) throws GenericProTipServiceException, TokenExpiredException {
        Objects.requireNonNull(token, TOKEN_NULL);

        JwtTokenUtil.validateToken(token);
        final Claims claims = JwtTokenUtil.getAllClaimsFromToken(token);
        final Role role = roleRepository.findByName(claims.get(CLAIM_ROLE).toString());
        if (role == null) throw new GenericProTipServiceException(SOMETHING_WRONG);

        return role;
    }
}
