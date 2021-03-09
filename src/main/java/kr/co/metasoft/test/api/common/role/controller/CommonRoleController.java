package kr.co.metasoft.test.api.common.role.controller;

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

import kr.co.metasoft.test.api.common.role.service.CommonRoleService;
import kr.co.metasoft.test.common.entity.jpa.RoleEntity;

@RestController
@RequestMapping (path = "api/common/roles")
public class CommonRoleController {

    @Autowired
    private CommonRoleService commonRoleService;

    @GetMapping (path = "")
    public Page<RoleEntity> getRoleList(
            @ModelAttribute RoleEntity roleEntity,
            @PageableDefault Pageable pageable) {
        return commonRoleService.getRoleList(roleEntity, pageable);
    }

    @GetMapping (path = "{id}")
    public RoleEntity getRole(
            @PathVariable (name = "id") String id) {
        return commonRoleService.getRole(id);
    }

    @PostMapping (path = "")
    public RoleEntity createRole(
            @RequestBody RoleEntity roleEntity) {
        return commonRoleService.createRole(roleEntity);
    }

    @PutMapping (path = "{id}")
    public RoleEntity modifyRole(
            @PathVariable (name = "id") String id,
            @RequestBody RoleEntity roleEntity) {
        roleEntity.setId(id);
        return commonRoleService.modifyRole(roleEntity);
    }

    @DeleteMapping (path = "{id}")
    public void removeRole(
            @PathVariable (name = "id") String id) {
        commonRoleService.removeRole(id);
    }

}