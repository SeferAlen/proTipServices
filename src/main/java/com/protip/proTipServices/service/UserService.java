package com.protip.proTipServices.service;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.utility.UserCreateStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

//Service for handling all user related actions
public interface UserService {

    public abstract UserCreateStatus createUser(final ProTipUser proTipUser, final String password);
    public abstract List<ProTipUser> getUsers();
    public abstract Login findByUsername(final String username);
}
