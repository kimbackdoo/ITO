package kr.co.metasoft.ito.api.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.app.dto.AccountDto;
import kr.co.metasoft.ito.api.app.service.AccountService;

@RestController
@RequestMapping (path = "api/app/accounts")
public class ApiAppAccountController {

    @Autowired
    AccountService accountService;

    @PostMapping (path = "")
    public AccountDto createAccount(@RequestBody AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    @PutMapping (path = "")
    public void modifyAccount(@RequestBody AccountDto accountDto) {
        accountService.modifyAccount(accountDto);
    }

    @DeleteMapping (path = "")
    public void removeAccount(@RequestBody AccountDto accountDto) {
        accountService.removeAccount(accountDto);
    }


}
