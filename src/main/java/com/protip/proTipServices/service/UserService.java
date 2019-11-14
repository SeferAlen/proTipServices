package com.protip.proTipServices.service;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.utility.UserCreateStatus;

import java.util.List;

public interface UserService {

    public abstract UserCreateStatus createUser(final ProTipUser proTipUser, final String password, final Role role);
    public abstract List<ProTipUser> getUsers();
    public abstract Login findByUsername(final String username);
}
