package com.protip.proTipServices.repository;

import com.protip.proTipServices.model.Message;
import com.protip.proTipServices.model.SendMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    @Query(value = "SELECT * FROM MESSAGE ORDER BY date_time DESC", nativeQuery = true)
    public abstract List<Message> getAllSorted();
}
