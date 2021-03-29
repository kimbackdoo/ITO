package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.PersonSkillParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonSkillEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface PersonSkillMapper {

    public List<PersonSkillEntity> selectPersonSkillList(
            @Param (value = "PersonSkillParamDto") PersonSkillParamDto personSkillParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectPersonSkillListCount(
            @Param (value = "personSkillParamDto") PersonSkillParamDto personSkillParamDto);

    public PersonSkillEntity selectPersonSkill(
            @Param (value = "personId") Long personId,
            @Param (value = "skill") String skill);

}