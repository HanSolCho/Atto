package com.example.ato.repository;

import com.example.ato.model.Account;
import com.example.ato.repository.mapper.AccountMapper;
import org.springframework.stereotype.Repository;


@Repository
public class AccountRepository {
    private AccountMapper mapper;
    public AccountRepository(AccountMapper mapper){
        this.mapper = mapper;
    }
    public void signUp(Account account){ mapper.signUp(account);}
    public Account signIn(Account account){ return mapper.signIn(account);}
    public String selectId(String id){ return mapper.selectId(id);}
}
