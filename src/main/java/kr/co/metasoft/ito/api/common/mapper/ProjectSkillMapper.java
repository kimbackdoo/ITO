package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.ProjectSkillParamDto;
import kr.co.metasoft.ito.api.common.entity.ProjectSkillEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface ProjectSkillMapper {

    public List<ProjectSkillEntity> selectProjectSkillList(
            @Param (value = "ProjectSkillParamDto") ProjectSkillParamDto projectSkillParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectProjectSkillListCount(
            @Param (value = "projectSkillParamDto") ProjectSkillParamDto projectSkillParamDto);

    public ProjectSkillEntity selectProjectSkill(
            @Param (value = "projectId") Long projectId,
            @Param (value = "skill") String skill);

}