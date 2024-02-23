package com.example.ato.service;

import com.example.ato.exception.CustomException;
import com.example.ato.exception.ErrorCode;
import com.example.ato.model.Account;
import com.example.ato.model.AccountRoleType;
import com.example.ato.repository.AccountRepository;
import com.example.ato.repository.MonitoringRepository;
import com.example.ato.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MonitoringRepository monitoringRepository;

    private TokenProvider tokenProvider;

    private PasswordEncoder encoder;

    public AccountService(PasswordEncoder encoder, TokenProvider tokenProvider){
        this.encoder = encoder;
        this.tokenProvider = tokenProvider;
    }
    @Transactional
    public void signUp(String id, String password, String roleType) {
        String encodePassword = encoder.encode(password);

        Account account = new Account();
        account.setId(id);
        account.setPassword(encodePassword);
        if(roleType.equals("admin")) {
            account.setAccountRoleType(AccountRoleType.ROLE_ADMIN);
        }else{
            account.setAccountRoleType(AccountRoleType.ROLE_USER);
        }

        if(!isUserExist(id)) {
            accountRepository.signUp(account);
            monitoringRepository.write(id,"회원가입 ID : " + id + "Type : " + roleType,"SUCESS");
        }
        else {
            monitoringRepository.write(id,"중복된 ID","FAIL");
            throw new CustomException(ErrorCode.DUPLICATE_USERID);
        }

    }

    private boolean isUserExist(String id) {
        return accountRepository.selectId(id) != null;
    }

    public Account signIn(String id, String password){
        String encodePassword = encoder.encode(password);

        Account account = new Account();
        account.setId(id);
        account.setPassword(encodePassword);

        Account accountCheck = accountRepository.signIn(account);

        if(encoder.matches(password,accountCheck.getPassword())){
            String token = tokenProvider.createToken(String.format("%s:%s", accountCheck.getId(), accountCheck.getAccountRoleType()));	// 토큰 생성
            Account responseAccount = new Account();
            responseAccount.setId(accountCheck.getId());
            responseAccount.setAccountRoleType(accountCheck.getAccountRoleType());
            responseAccount.setToken(token);

            monitoringRepository.write(id,"로그인","SUCESS");
            return responseAccount;
        }else{
            monitoringRepository.write(id,"로그인","FAIL");
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCOUNT);
        }

    }
}
