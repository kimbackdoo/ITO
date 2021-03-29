package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.PersonSectorParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonSectorEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface PersonSectorMapper {

    public List<PersonSectorEntity> selectPersonSectorList(
            @Param (value = "PersonSectorParamDto") PersonSectorParamDto personSectorParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectPersonSectorListCount(
            @Param (value = "personSectorParamDto") PersonSectorParamDto personSectorParamDto);

    public PersonSectorEntity selectPersonSector(
            @Param (value = "personId") Long personId,
            @Param (value = "sector") String sector);

}