package kr.co.metasoft.ito.api.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import kr.co.metasoft.ito.api.common.dto.RoleApiParamDto;
import kr.co.metasoft.ito.api.common.dto.RoleDto;
import kr.co.metasoft.ito.api.common.dto.RoleMenuParamDto;
import kr.co.metasoft.ito.api.common.dto.RoleParamDto;
import kr.co.metasoft.ito.api.common.entity.RoleApiEntity;
import kr.co.metasoft.ito.api.common.entity.RoleEntity;
import kr.co.metasoft.ito.api.common.entity.RoleMenuEntity;
import kr.co.metasoft.ito.api.common.mapper.RoleApiMapper;
import kr.co.metasoft.ito.api.common.mapper.RoleMapper;
import kr.co.metasoft.ito.api.common.mapper.RoleMenuMapper;
import kr.co.metasoft.ito.api.common.repository.RoleApiRepository;
import kr.co.metasoft.ito.api.common.repository.RoleMenuRepository;
import kr.co.metasoft.ito.api.common.repository.RoleRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

@Validated
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private RoleApiRepository roleApiRepository;

    @Autowired
    private RoleApiMapper roleApiMapper;

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public PageResponse<RoleEntity> getRoleList(
            @Valid RoleParamDto roleParamDto,
            PageRequest pageRequest) {
        Integer roleListCount = roleMapper.selectRoleListCount(roleParamDto);
        List<RoleEntity> roleList = roleMapper.selectRoleList(roleParamDto, pageRequest);
        PageResponse<RoleEntity> pageResponse = new PageResponse<>(pageRequest, roleListCount);
        pageResponse.setItems(roleList);
        return pageResponse;
    }

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public RoleEntity getRole(
            @Valid @NotNull (groups = {ReadValidationGroup.class}) Long id) {
        return roleMapper.selectRole(id);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public List<RoleEntity> createRoleList(
            @Valid @NotEmpty (groups = {CreateValidationGroup.class}) List<@NotNull (groups = {CreateValidationGroup.class}) RoleEntity> roleList) {
        return roleRepository.saveAll(roleList);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public RoleEntity createRole(
            @Valid @NotNull (groups = {CreateValidationGroup.class}) RoleEntity roleEntity) {
        return roleRepository.save(roleEntity);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public String createRoleAndMenuListAndApiList(
            RoleDto roleDto) {
        RoleEntity roleEntity = RoleEntity.builder()
                .name(roleDto.getName())
                .description(roleDto.getDescription())
                .value(roleDto.getValue())
                .build();
        RoleEntity resultRoleEntity = roleRepository.save(roleEntity);
        if(roleDto.getRoleMenuListIdString() != null && !roleDto.getRoleMenuListIdString().equals(""))
        {
            String[] roleMenuListIdString = roleDto.getRoleMenuListIdString().split(",");
            for(Integer i = 0; i< roleMenuListIdString.length;i++)
            {
                RoleMenuEntity roleMenuEntity = RoleMenuEntity.builder()
                        .roleId(resultRoleEntity.getId())
                        .menuId(Long.valueOf(roleMenuListIdString[i]))
                        .build();
                roleMenuRepository.save(roleMenuEntity);
            }
        }
        if(roleDto.getRoleApiListIdString() != null && !roleDto.getRoleApiListIdString().equals(""))
        {
            String[] roleApiListIdString = roleDto.getRoleApiListIdString().split(",");
            for(Integer i = 0; i< roleApiListIdString.length;i++)
            {
                RoleApiEntity roleApiEntity = RoleApiEntity.builder()
                        .roleId(resultRoleEntity.getId())
                        .apiId(Long.valueOf(roleApiListIdString[i]))
                        .build();
                roleApiRepository.save(roleApiEntity);
            }
        }
        return "success";
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public String updateAllAuth(
            RoleDto roleDto) {
        RoleEntity resultRoleEntity = roleRepository.getOne(roleDto.getId());
        resultRoleEntity.setName(roleDto.getName());
        resultRoleEntity.setDescription(roleDto.getDescription());
        resultRoleEntity.setValue(roleDto.getValue());
        roleRepository.save(resultRoleEntity);
        deleteAndUpdateRoleMenuList(roleDto,resultRoleEntity);
        deleteAndUpdateRoleApiList(roleDto,resultRoleEntity);
        return "success";
    }

    public void deleteAndUpdateRoleMenuList(RoleDto roleDto , RoleEntity resultRoleEntity) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setRowSize(1000000);
        RoleMenuParamDto roleMenuParamDto = new RoleMenuParamDto();
        roleMenuParamDto.setRoleId(roleDto.getId().toString());
        String[] roleMenuListIdString = roleDto.getRoleMenuListIdString().split(",");
        List<String> roleMenuListIdList = new ArrayList<>();
        if(roleDto.getRoleMenuListIdString() != null && !roleDto.getRoleMenuListIdString().equals(""))
            Collections.addAll(roleMenuListIdList, roleMenuListIdString);
        List<RoleMenuEntity> roleMenuList = roleMenuMapper.selectRoleMenuList(roleMenuParamDto, pageRequest);
        List<RoleMenuEntity> roleMenuDeleteList = new ArrayList<>();
        for(RoleMenuEntity roleMenu : roleMenuList)
        {
            if(!roleMenuListIdList.contains(roleMenu.getMenuId().toString()))
            {
                roleMenuDeleteList.add(roleMenu);
            }
            if(roleMenuListIdList.contains(roleMenu.getMenuId().toString()))
            {
                roleMenuListIdList.remove(roleMenuListIdList.indexOf(roleMenu.getMenuId().toString()));
            }
        }
        if(!roleMenuDeleteList.isEmpty())
            roleMenuRepository.deleteAll(roleMenuDeleteList);
        for(Integer i = 0; i< roleMenuListIdList.size();i++)
        {
            RoleMenuEntity roleMenuEntity = RoleMenuEntity.builder()
                    .roleId(resultRoleEntity.getId())
                    .menuId(Long.valueOf(roleMenuListIdList.get(i)))
                    .build();
            roleMenuRepository.save(roleMenuEntity);
        }
    }

    public void deleteAndUpdateRoleApiList(RoleDto roleDto , RoleEntity resultRoleEntity) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setRowSize(1000000);
        String[] roleApiListIdString = roleDto.getRoleApiListIdString().split(",");
        RoleApiParamDto roleApiParamDto = new RoleApiParamDto();
        roleApiParamDto.setRoleId(roleDto.getId());
        List<String> roleApiListIdList = new ArrayList<>();
        if(roleDto.getRoleApiListIdString() != null && !roleDto.getRoleApiListIdString().equals(""))
            Collections.addAll(roleApiListIdList, roleApiListIdString);
        List<RoleApiEntity> roleApiList = roleApiMapper.selectRoleApiList(roleApiParamDto, pageRequest);
        List<RoleApiEntity> roleApiDeleteList = new ArrayList<>();
        for(RoleApiEntity roleApi : roleApiList)
        {
            if(!roleApiListIdList.contains(roleApi.getApiId().toString()))
            {
                roleApiDeleteList.add(roleApi);
            }
            if(roleApiListIdList.contains(roleApi.getApiId().toString()))
            {
                roleApiListIdList.remove(roleApiListIdList.indexOf(roleApi.getApiId().toString()));
            }
        }
        if(!roleApiDeleteList.isEmpty())
            roleApiRepository.deleteAll(roleApiDeleteList);
        for(Integer i = 0; i< roleApiListIdList.size();i++)
        {
            RoleApiEntity roleApiEntity = RoleApiEntity.builder()
                    .roleId(resultRoleEntity.getId())
                    .apiId(Long.valueOf(roleApiListIdList.get(i)))
                    .build();
            roleApiRepository.save(roleApiEntity);
        }
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public List<RoleEntity> modifyRoleList(
            @Valid @NotEmpty (groups = {ModifyValidationGroup.class}) List<@NotNull (groups = {ModifyValidationGroup.class}) RoleEntity> roleList) {
        return roleRepository.saveAll(roleList);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public RoleEntity modifyRole(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) RoleEntity roleEntity) {
        return roleRepository.save(roleEntity);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removeRoleList(
            @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) Long> idList) {
        List<RoleEntity> roleList = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            Long id = idList.get(i);
            roleList.add(RoleEntity.builder().id(id).build());
        }
        roleRepository.deleteAll(roleList);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removeRoleAllDependencyList(
            @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) Long> idList) {
        List<RoleEntity> roleList = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPage(1);
            pageRequest.setRowSize(1000000);
            RoleMenuParamDto roleMenuParamDto = new RoleMenuParamDto();
            roleMenuParamDto.setRoleId(idList.get(i).toString());
            List<RoleMenuEntity> roleMenuList = roleMenuMapper.selectRoleMenuList(roleMenuParamDto, pageRequest);
            if(!roleMenuList.isEmpty())
                roleMenuRepository.deleteAll(roleMenuList);
            RoleApiParamDto roleApiParamDto = new RoleApiParamDto();
            roleApiParamDto.setRoleId(idList.get(i));
            List<RoleApiEntity> roleApiList = roleApiMapper.selectRoleApiList(roleApiParamDto, pageRequest);
            if(!roleApiList.isEmpty())
                roleApiRepository.deleteAll(roleApiList);
            Long id = idList.get(i);
            roleList.add(RoleEntity.builder().id(id).build());
        }
        roleRepository.deleteAll(roleList);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removeRole(
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) Long id) {
        roleRepository.delete(RoleEntity.builder().id(id).build());
    }

}