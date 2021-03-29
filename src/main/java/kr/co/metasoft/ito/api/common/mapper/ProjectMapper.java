package kr.co.metasoft.ito.api.common.mapper;

import kr.co.metasoft.ito.api.common.dto.ProjectParamDto;
import kr.co.metasoft.ito.api.common.entity.ProjectEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectMapper {
    Integer selectProjectListCount(
            @Param(value = "projectParamDto") ProjectParamDto projectParamDto);

    List<ProjectEntity> selectProjectList(
            @Param(value = "projectParamDto") ProjectParamDto projectParamDto,
            @Param(value = "pageRequest")PageRequest pageRequest);


}
