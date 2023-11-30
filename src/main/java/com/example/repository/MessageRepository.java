package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
    //to be continued
}
