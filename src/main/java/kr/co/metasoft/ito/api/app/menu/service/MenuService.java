package kr.co.metasoft.ito.api.app.menu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.metasoft.ito.api.app.menu.dto.MenuDto;
import kr.co.metasoft.ito.api.app.menu.dto.MenuParamDto;
import kr.co.metasoft.ito.api.app.menu.mapper.MenuMapper;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Transactional
    public List<MenuDto> getMenuList(MenuParamDto menuParamDto) {
        return menuMapper.selectMenuList(menuParamDto);
    }

}