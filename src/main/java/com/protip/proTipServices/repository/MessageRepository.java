package com.protip.proTipServices.repository;

import com.protip.proTipServices.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query(value = "SELECT * FROM MESSAGE ORDER BY date_time DESC", nativeQuery = true)
    public abstract List<Message> getAllSorted();

    @Query(value = "DELETE FROM MESSAGE WHERE id_message IN (" +
                        "SELECT id_message FROM (" +
                            "SELECT m.id_message, t.name FROM MESSAGE m INNER JOIN MESSAGE_TYPE t" +
                                "ON m.message_type_id_message_type = t.id_message_type WHERE t.name = ?1" +
                                    "ORDER BY m.date_time LIMIT 30, 1000" +
                                        ") a " +
                                            ")", nativeQuery = true)
    public abstract List<Message> deleteOlderMessages(final String name);
}
