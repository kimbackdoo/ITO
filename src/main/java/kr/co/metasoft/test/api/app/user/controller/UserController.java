package kr.co.metasoft.test.api.app.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.test.api.app.user.dto.UserPersonDto;
import kr.co.metasoft.test.api.app.user.service.UserService;
import kr.co.metasoft.test.common.entity.jpa.UserEntity;

@RestController
@RequestMapping (path = "api/app/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping (path = "by-token")
    public UserEntity getUserByToken(
            @RequestParam (name = "token") String token) {
        return userService.getUserByToken(token);
    }

    @PostMapping (path = "sign-up")
    public void signUp(
            @RequestBody UserPersonDto userPersonDto)
                    throws Exception {
        userService.signUp(userPersonDto);
    }

}