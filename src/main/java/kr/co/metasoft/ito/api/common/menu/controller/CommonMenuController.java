package kr.co.metasoft.ito.api.common.menu.controller;

import java.util.Base64;

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

import kr.co.metasoft.ito.api.common.menu.service.CommonMenuService;
import kr.co.metasoft.ito.common.entity.jpa.MenuEntity;

@RestController
@RequestMapping (path = "api/common/menus")
public class CommonMenuController {

    @Autowired
    private CommonMenuService commonMenuService;

    @GetMapping (path = "")
    public Page<MenuEntity> getMenuList(
            @ModelAttribute MenuEntity menuEntity,
            @PageableDefault Pageable pageable) {
        return commonMenuService.getMenuList(menuEntity, pageable);
    }

    @GetMapping (path = "{id}")
    public MenuEntity getMenu(
            @PathVariable (name = "id") String id) {
        id = new String(Base64.getDecoder().decode(id));
        return commonMenuService.getMenu(id);
    }

    @PostMapping (path = "")
    public MenuEntity createMenu(
            @RequestBody MenuEntity menuEntity) {
        return commonMenuService.createMenu(menuEntity);
    }

    @PutMapping (path = "{id}")
    public MenuEntity modifyMenu(
            @PathVariable (name = "id") String id,
            @RequestBody MenuEntity menuEntity) {
        id = new String(Base64.getDecoder().decode(id));
        menuEntity.setId(id);
        return commonMenuService.modifyMenu(menuEntity);
    }

    @DeleteMapping (path = "{id}")
    public void removeMenu(
            @PathVariable (name = "id") String id) {
        id = new String(Base64.getDecoder().decode(id));
        commonMenuService.removeMenu(id);
    }

}