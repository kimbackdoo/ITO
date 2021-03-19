package kr.co.metasoft.ito.api.common.mapper;

import kr.co.metasoft.ito.api.common.dto.ProjectDto;
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
            @Param(value = "projectDto")ProjectDto projectDto);

    List<ProjectEntity> selectProjectList(
            @Param(value = "projectDto") ProjectDto projectDto,
            @Param(value = "pageRequest")PageRequest pageRequest);
}
