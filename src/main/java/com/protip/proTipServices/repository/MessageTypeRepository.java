package com.protip.proTipServices.repository;

import com.protip.proTipServices.model.MessageType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageTypeRepository extends JpaRepository<MessageType, UUID> {

    @Cacheable("messageType")
    public abstract MessageType findByName(final String name);
}
