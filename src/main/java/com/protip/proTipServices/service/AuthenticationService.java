package com.protip.proTipServices.service;

import com.protip.proTipServices.model.Login;

public interface AuthenticationService {

    String loginAndGenerateToken(final Login login);
}
