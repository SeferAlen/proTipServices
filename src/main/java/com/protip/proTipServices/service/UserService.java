package com.protip.proTipServices.service;

import com.protip.proTipServices.model.ProTipUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

//Service for handling all user related actions
public interface UserService {

    public abstract void createUser(final ProTipUser proTipUser, final String password);
    public abstract List<ProTipUser> getUsers();
}
