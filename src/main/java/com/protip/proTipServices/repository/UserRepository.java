package com.protip.proTipServices.repository;

import com.protip.proTipServices.model.ProTipUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<ProTipUser, UUID> {

    @Query(value = "SELECT pro_tip_user_validity_date FROM USERS WHERE id_user = ?1", nativeQuery = true)
    public abstract String getValidityDate(final UUID idUser);
}
