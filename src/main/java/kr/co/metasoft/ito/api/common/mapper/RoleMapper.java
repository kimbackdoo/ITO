package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.RoleParamDto;
import kr.co.metasoft.ito.api.common.entity.RoleEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface RoleMapper {

    public List<RoleEntity> selectRoleList(
            @Param (value = "roleParamDto") RoleParamDto roleParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectRoleListCount(
            @Param (value = "roleParamDto") RoleParamDto roleParamDto);

    public RoleEntity selectRole(
            @Param (value = "id") Long id);

}