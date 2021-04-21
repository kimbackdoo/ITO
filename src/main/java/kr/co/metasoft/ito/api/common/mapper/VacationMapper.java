package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.VacationParamDto;
import kr.co.metasoft.ito.api.common.entity.VacationEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Mapper
@Repository
public interface VacationMapper {

    public VacationEntity selectVacation(
            @Param (value = "id") Long id);

    public List<VacationEntity> selectVacationList(
            @Param (value = "vacationParamDto") VacationParamDto vacationParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public int selectVacationListCount(
            @Param (value = "vacationParamDto") VacationParamDto vacationParamDto);



}
