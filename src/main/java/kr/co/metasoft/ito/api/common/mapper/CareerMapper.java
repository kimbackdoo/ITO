package kr.co.metasoft.ito.api.common.mapper;

import kr.co.metasoft.ito.api.common.dto.CareerDto;
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
            @Param(value = "careerDto") CareerDto careerDto);

    List<CareerEntity> selectCareerList(
            @Param(value = "careerDto") CareerDto careerDto,
            @Param(value = "pageRequest") PageRequest pageRequest);

    void deleteCareerList(@Param(value = "list") List<Long> careerDtoList);
}
