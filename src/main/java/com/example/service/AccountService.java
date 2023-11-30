package com.example.service;

import com.example.repository.AccountRepository;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import java.util.List;
@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }



    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Boolean accountExists(Integer id){
        return accountRepository.existsById(id);
    }
    public Account createAccount(Account account){

        for(Account acc : this.getAllAccounts()){
            if(acc.getUsername().equals(account.getUsername()) ){
                return acc;
           }
        }
        if(account.getUsername() != "" && account.getPassword().length() >= 4){
            return accountRepository.save(account);
        }
        
        return null;

    }

    public Account authenticateAccount(Account account){
        for(Account acc : this.getAllAccounts()){
            if(acc.getUsername().equals(account.getUsername()) && acc.getPassword().equals(account.getPassword())){
                return acc;
            }
        }
        return null;
    }
}
