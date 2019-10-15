package com.protip.proTipServices.service;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.utility.UserCreateStatus;
import com.protip.proTipServices.utility.UserTokenStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

//Service for handling all user related actions
public interface UserService {

    public abstract UserCreateStatus createUser(final ProTipUser proTipUser, final String password, final Role role);
    public abstract List<ProTipUser> getUsers();
    public abstract Login findByUsername(final String username);
    public abstract UserTokenStatus setToken(final String token, final String  username);
}
