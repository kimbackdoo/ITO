package kr.co.metasoft.ito.api.common.mapper;

import kr.co.metasoft.ito.api.common.dto.PersonCareerParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonCareerEntity;
import kr.co.metasoft.ito.common.util.PageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PersonCareerMapper {
    List<PersonCareerEntity> selectPersonCareerList(
            @Param(value = "personCareerParamDto")PersonCareerParamDto personCareerParamDto,
            @Param(value = "pageRequest")PageRequest pageRequest);

    Integer selectPersonCareerListCount(
            @Param(value = "personCareerParamDto") PersonCareerParamDto personCareerParamDto);

    PersonCareerEntity selectPersonCareer(
            @Param(value = "personId") Long personId,
            @Param(value = "careerId") Long careerId);
}
