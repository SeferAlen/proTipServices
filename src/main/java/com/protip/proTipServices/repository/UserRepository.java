package com.protip.proTipServices.repository;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<ProTipUser, UUID> {
}
