package com.example.ato.controller;

import com.example.ato.model.Account;
import com.example.ato.service.AccountService;
import com.example.ato.util.JsonControllerUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AccountController { //회원 가입 api
    private JsonControllerUtil util;
    private AccountService accountService;
    private PasswordEncoder encoder;

    public AccountController(JsonControllerUtil util, AccountService accountService, PasswordEncoder encoder) {
        this.util = util;
        this.accountService = accountService;
        this.encoder = encoder;
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(HttpServletRequest httpRequest,
                                         @RequestParam(value = "id", required = false) String id,
                                         @RequestParam(value = "password", required = false) String password,
                                         @RequestParam(value = "roleType", required = false) String roleType) {

        accountService.signUp(id, password, roleType);
        return util.getResponseEntity();
    }

    //로그인
    @PostMapping("signIn")
    public ResponseEntity<String> signIn(HttpServletRequest httpRequest,
                                         @RequestParam(value = "id", required = false) String id,
                                         @RequestParam(value = "password", required = false) String password) {

        Account account = accountService.signIn(id, password);
        return util.getJsonResponseEntity(account);
    }

}
