package kr.co.metasoft.ito.api.common.mapper;

import kr.co.metasoft.ito.api.common.dto.PersonParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.common.util.PageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PersonProjectGroupMapper {
    List<PersonEntity> selectPersonProjectList(
            @Param(value = "personParamDto") PersonParamDto personParamDto,
            @Param(value = "pageRequest") PageRequest pageRequest);
}
