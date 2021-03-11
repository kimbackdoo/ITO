package kr.co.metasoft.ito.api.app.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.app.menu.dto.MenuDto;
import kr.co.metasoft.ito.api.app.menu.dto.MenuParamDto;
import kr.co.metasoft.ito.api.app.menu.service.MenuService;

@RestController
@RequestMapping (path = "api/app/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping (path = "")
    public List<MenuDto> getMenuList(
            @ModelAttribute MenuParamDto menuParamDto) {
        return menuService.getMenuList(menuParamDto);
    }

}