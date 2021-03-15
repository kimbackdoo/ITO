package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.UserPersonParamDto;
import kr.co.metasoft.ito.api.common.entity.UserPersonEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface UserPersonMapper {

    public List<UserPersonEntity> selectUserPersonList(
            @Param (value = "userPersonParamDto") UserPersonParamDto userPersonParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectUserPersonListCount(
            @Param (value = "userPersonParamDto") UserPersonParamDto userPersonParamDto);

    public UserPersonEntity selectUserPerson(
            @Param (value = "userId") Long userId);

}