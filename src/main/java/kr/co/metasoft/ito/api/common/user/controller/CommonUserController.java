package kr.co.metasoft.ito.api.common.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.common.user.service.CommonUserService;
import kr.co.metasoft.ito.common.entity.jpa.UserEntity;

@RestController
@RequestMapping (path = "api/common/users")
public class CommonUserController {

    @Autowired
    private CommonUserService commonUserService;

    @GetMapping (path = "")
    public Page<UserEntity> getUserList(
            @ModelAttribute UserEntity userEntity,
            @PageableDefault Pageable pageable) {
        return commonUserService.getUserList(userEntity, pageable);
    }

    @GetMapping (path = "{id}")
    public UserEntity getUser(
            @PathVariable (name = "id") String id) {
        return commonUserService.getUser(id);
    }

    @PostMapping (path = "")
    public UserEntity createUser(
            @RequestBody UserEntity userEntity) {
        return commonUserService.createUser(userEntity);
    }

    @PutMapping (path = "{id}")
    public UserEntity modifyUser(
            @PathVariable (name = "id") String id,
            @RequestBody UserEntity userEntity) {
        userEntity.setId(id);
        return commonUserService.modifyUser(userEntity);
    }

    @DeleteMapping (path = "{id}")
    public void removeUser(
            @PathVariable (name = "id") String id) {
        commonUserService.removeUser(id);
    }

}