package com.example.ato.repository.mapper;

import com.example.ato.model.Account;

public interface AccountMapper {
    void signUp(Account account);
    Account signIn(Account account);
    String selectId(String id);
}
