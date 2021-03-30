package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.ProjectPersonParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.entity.ProjectPersonEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface ProjectPersonMapper {

    public List<PersonEntity> selectProjectPersonList(
            @Param (value = "projectPersonParamDto") ProjectPersonParamDto projectPersonParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectProjectPersonListCount(
            @Param (value = "projectPersonParamDto") ProjectPersonParamDto projectPersonParamDto);


}
