package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.PersonParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface PersonMapper {

    public List<PersonEntity> selectPersonList(
            @Param (value = "personParamDto") PersonParamDto personParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectPersonListCount(
            @Param (value = "personParamDto") PersonParamDto personParamDto);

    public PersonEntity selectPerson(
            @Param (value = "id") Long id);

    public void insertXlsx(
            @Param (value = "list") List<PersonEntity> param );
}