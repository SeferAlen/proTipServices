package com.protip.proTipServices.repository;

import com.protip.proTipServices.model.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageTypeRepository extends JpaRepository<MessageType, UUID> {

    public abstract MessageType findByName(final String name);
}
