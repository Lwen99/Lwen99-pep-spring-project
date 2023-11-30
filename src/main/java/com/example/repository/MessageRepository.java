package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
    //to be continued
    @Query("SELECT m FROM Message m WHERE m.posted_by = :id")
    List<Message> findByposted_by(Integer id);
}
