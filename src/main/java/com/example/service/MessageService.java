package com.example.service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.stereotype.Service;

import com.example.SocialMediaApp;
import com.example.entity.Account;
import com.example.entity.Message;
import org.springframework.context.annotation.Bean;

import java.util.List;
@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }
    
    public Message createMessage(Message message, AccountService accountService){

       
        if(accountService.accountExists(message.getPosted_by()) && message.getMessage_text() != "" 
            && message.getMessage_text().length() <= 255){
            return messageRepository.save(message);
        }
        return null;
    }

    public Message getMessageById(Integer id){
        return messageRepository.findById(id).orElse(null);
    }

    
    public String deleteMessageById(Integer id){
        if(messageRepository.existsById(id)){
            messageRepository.deleteById(id);
            return "1";
        }
        return "";
    
    }

    public String updateMessageById(Integer id, String update_text){
        if(messageRepository.existsById(id)){
            messageRepository.findById(id).get().setMessage_text(update_text);
            messageRepository.save(messageRepository.findById(id).get());
            return "1";
        }
        return "";
    }

    public List<Message> getMessagesByUserId(Integer id){
      
        return messageRepository.findByposted_by(id);
    }
}
