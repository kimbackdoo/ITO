package kr.co.metasoft.ito.api.common.mapper;

import kr.co.metasoft.ito.api.common.dto.CareerParamDto;
import kr.co.metasoft.ito.api.common.entity.CareerEntity;
import kr.co.metasoft.ito.common.util.PageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CareerMapper {
    Integer selectCareerListCount(
            @Param(value = "careerParamDto") CareerParamDto careerParamDto);

    List<CareerEntity> selectCareerList(
            @Param(value = "careerParamDto") CareerParamDto careerParamDto,
            @Param(value = "pageRequest") PageRequest pageRequest);

    void deleteCareerList(@Param(value = "list") List<Long> careerDtoList);
}
