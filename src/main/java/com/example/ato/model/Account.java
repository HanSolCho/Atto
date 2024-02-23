package com.example.ato.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class Account {
    private String id;
    private String password;
    @Enumerated(EnumType.STRING)
    private AccountRoleType accountRoleType;
    private String token;
}
