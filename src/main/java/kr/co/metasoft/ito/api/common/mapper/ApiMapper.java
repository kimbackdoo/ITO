package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.ApiParamDto;
import kr.co.metasoft.ito.api.common.entity.ApiEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface ApiMapper {

    public List<ApiEntity> selectApiList(
            @Param (value = "apiParamDto") ApiParamDto apiParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectApiListCount(
            @Param (value = "apiParamDto") ApiParamDto apiParamDto);

    public ApiEntity selectApi(
            @Param (value = "id") Long id);

}