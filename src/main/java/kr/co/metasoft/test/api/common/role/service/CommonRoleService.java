package kr.co.metasoft.test.api.common.role.service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.metasoft.test.common.entity.jpa.RoleEntity;
import kr.co.metasoft.test.common.repository.jpa.RoleRepository;

@Service
public class CommonRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Page<RoleEntity> getRoleList(
            RoleEntity roleEntity,
            Pageable pageable) {
        return roleRepository.findAll(Example.of(roleEntity), pageable);
    }

    @Transactional
    public RoleEntity getRole(String id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Transactional
    public RoleEntity createRole(RoleEntity roleEntity) {
        return roleRepository.save(roleEntity);
    }

    @Transactional
    public RoleEntity modifyRole(RoleEntity roleEntity) {
        return roleRepository.save(roleEntity);
    }

    @Transactional
    public void removeRole(String id) {
        roleRepository.deleteById(id);
    }

}