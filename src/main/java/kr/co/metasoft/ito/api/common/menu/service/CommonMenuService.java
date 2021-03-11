package kr.co.metasoft.ito.api.common.menu.service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.metasoft.ito.common.entity.jpa.MenuEntity;
import kr.co.metasoft.ito.common.repository.jpa.MenuRepository;

@Service
public class CommonMenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Transactional
    public Page<MenuEntity> getMenuList(
            MenuEntity menuEntity,
            Pageable pageable) {
        return menuRepository.findAll(Example.of(menuEntity), pageable);
    }

    @Transactional
    public MenuEntity getMenu(String id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Transactional
    public MenuEntity createMenu(MenuEntity menuEntity) {
        return menuRepository.save(menuEntity);
    }

    @Transactional
    public MenuEntity modifyMenu(MenuEntity menuEntity) {
        return menuRepository.save(menuEntity);
    }

    @Transactional
    public void removeMenu(String id) {
        menuRepository.deleteById(id);
    }

}