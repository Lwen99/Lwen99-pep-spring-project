package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.entity.Account;
import com.example.entity.Message;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService ){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        Account acc = accountService.createAccount(account);
        if(acc.equals(account)){
            return new ResponseEntity<Account>(acc, HttpStatus.OK);
        
        }
        if(acc == null){
            return new ResponseEntity<Account>(acc, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Account>(acc, HttpStatus.CONFLICT);
        
    }

    @PostMapping("/login")
    public ResponseEntity<Account> authenticateAccount(@RequestBody Account account){
        Account acc = accountService.authenticateAccount(account);
        if(acc != null){
            return new ResponseEntity<Account>(acc, HttpStatus.OK);
        }
        return new ResponseEntity<Account>(account, HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message mesg = messageService.createMessage(message, accountService);
        if(mesg != null){
            return new ResponseEntity<Message>(mesg, HttpStatus.OK);

        }
        return new ResponseEntity<Message>(mesg, HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return new ResponseEntity<List<Message>>(messageService.getAllMessages(), HttpStatus.OK);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer message_id){
        Message msg = messageService.getMessageById(message_id);
        return new ResponseEntity<Message>(msg, HttpStatus.OK);
        

    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer message_id){
        String str = messageService.deleteMessageById(message_id);
        return new ResponseEntity<String>(str, HttpStatus.OK);
    
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<String> updateMessageById(@PathVariable Integer message_id, @RequestBody String update_text){
        try{
            ObjectMapper obj = new ObjectMapper();
            JsonNode js = obj.readTree(update_text);
            String text = js.get("message_text").asText();

            if(text != "" && text.length() <= 255){
                String str = messageService.updateMessageById(message_id, update_text);
                if(str != ""){
                    return new ResponseEntity<String>(str, HttpStatus.OK);
                }
            }
        }catch(Exception e){
            
        }
      
        return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
       
    }

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Integer account_id){
        List<Message> msgs = messageService.getMessagesByUserId(account_id);
        return new ResponseEntity<List<Message>>(msgs, HttpStatus.OK);
    }
}

