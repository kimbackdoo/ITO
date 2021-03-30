package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.PersonLanguageParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonLanguageEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface PersonLanguageMapper {

    public List<PersonLanguageEntity> selectPersonLanguageList(
            @Param (value = "personLanguageParamDto") PersonLanguageParamDto personLanguageParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectPersonLanguageListCount(
            @Param (value = "personLanguageParamDto") PersonLanguageParamDto personLanguageParamDto);

    public PersonLanguageEntity selectPersonLanguage(
            @Param (value = "personId") Long personId,
            @Param (value = "language") String language);

}