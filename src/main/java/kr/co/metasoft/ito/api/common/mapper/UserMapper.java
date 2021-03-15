package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.UserParamDto;
import kr.co.metasoft.ito.api.common.entity.UserEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface UserMapper {

    public List<UserEntity> selectUserList(
            @Param (value = "userParamDto") UserParamDto userParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectUserListCount(
            @Param (value = "userParamDto") UserParamDto userParamDto);

    public UserEntity selectUser(
            @Param (value = "id") Long id);

    public UserEntity selectUserByUsername(
            @Param (value = "username") String username);

}