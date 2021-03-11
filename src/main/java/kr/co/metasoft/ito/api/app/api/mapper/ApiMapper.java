package kr.co.metasoft.ito.api.app.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.app.api.dto.ApiDto;
import kr.co.metasoft.ito.common.dto.PageRequestDto;

@Repository
@Mapper
public interface ApiMapper {

    public static final String API_QUERY = ""
            + "SELECT "
            + "    url, "
            + "    method, "
            + "    name, "
            + "    description "
            + "FROM "
            + "    tb_api ";

    @Select (value = "<script>"
            + ApiMapper.API_QUERY
            + PageRequestDto.LIMIT_OFFSET_QUERY
            + "</script>")
    public List<ApiDto> selectApiList(
            @Param (value = "pageRequestDto") PageRequestDto pageRequestDto);

    @Select (value = "<script>"
            + PageRequestDto.COUNT_START_QUERY
            + ApiMapper.API_QUERY
            + PageRequestDto.COUNT_END_QUERY
            + "</script>")
    public Integer selectApiListCount();

}