package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.ProjectCareerParamDto;
import kr.co.metasoft.ito.api.common.entity.ProjectCareerEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface ProjectCareerMapper {

    public List<ProjectCareerEntity> selectProjectCareerList(
            @Param (value = "projectCareerParamDto") ProjectCareerParamDto projectCareerParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectProjectCareerListCount(
            @Param (value = "projectCareerParamDto") ProjectCareerParamDto projectCareerParamDto);

    public List<ProjectCareerEntity> selectCareerIdList (
            @Param(value = "projectCareerParamDto") ProjectCareerParamDto projectCareerParamDto);

}
