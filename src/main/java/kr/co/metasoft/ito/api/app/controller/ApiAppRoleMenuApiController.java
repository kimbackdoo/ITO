package kr.co.metasoft.ito.api.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.app.service.RoleMenuApiService;
import kr.co.metasoft.ito.api.common.dto.RoleDto;

@RestController
@RequestMapping (path = "api/app/role-menu-apis")
public class ApiAppRoleMenuApiController {

    @Autowired
    RoleMenuApiService roleMenuApiService;

    @PostMapping (path = "")
    public String createRoleAndMenuListAndApiList(
            @RequestBody RoleDto roleDto) {
        return roleMenuApiService.createRoleMenuApi(roleDto);
    }

    @PutMapping (path = "")
    public String updateRoleAndMenuListAndApiList(
            @RequestBody RoleDto roleDto) {
        return roleMenuApiService.updateRoleMenuApi(roleDto);
    }
}
