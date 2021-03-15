package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.RoleUserParamDto;
import kr.co.metasoft.ito.api.common.entity.RoleUserEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface RoleUserMapper {

    public List<RoleUserEntity> selectRoleUserList(
            @Param (value = "roleUserParamDto") RoleUserParamDto roleUserParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectRoleUserListCount(
            @Param (value = "roleUserParamDto") RoleUserParamDto roleUserParamDto);

    public RoleUserEntity selectRoleUser(
            @Param (value = "userId") Long userId);

}