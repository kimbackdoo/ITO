package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.CodeParamDto;
import kr.co.metasoft.ito.api.common.entity.CodeEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface CodeMapper {

    public List<CodeEntity> selectCodeList(
            @Param (value = "codeParamDto") CodeParamDto codeParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectCodeListCount(
            @Param (value = "codeParamDto") CodeParamDto codeParamDto);

    public CodeEntity selectCode(
            @Param (value = "id") String id);

}
