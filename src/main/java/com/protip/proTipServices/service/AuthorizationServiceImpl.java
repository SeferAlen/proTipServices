package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.repository.RoleRepository;
import com.protip.proTipServices.utility.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    RoleRepository roleRepository;

    public boolean authorizeUser(final String token) throws GenericProTipServiceException, TokenExpiredException {
        Objects.requireNonNull(token, "Token must not be null");

        final Role role = basicAuthorization(token);

        if(roleRepository.findByName("USER") == role || roleRepository.findByName("ADMIN") == role) {
            return true;
        } else {
            return false;
        }
    }

    public boolean authorizeAdmin(final String token) throws GenericProTipServiceException, TokenExpiredException {
        Objects.requireNonNull(token, "Token must not be null");

        final Role role = basicAuthorization(token);

        if(roleRepository.findByName("ADMIN") == role) {
            return true;
        } else {
            return false;
        }
    }

    private Role basicAuthorization(final String token) throws GenericProTipServiceException, TokenExpiredException {
        JwtTokenUtil.validateToken(token);

        final Claims claims = JwtTokenUtil.getAllClaimsFromToken(token);
        final Role role = roleRepository.findByName(claims.get("Role").toString());
        if(role == null) throw new GenericProTipServiceException("Something wrong about roles or token");

        return role;
    }
}
