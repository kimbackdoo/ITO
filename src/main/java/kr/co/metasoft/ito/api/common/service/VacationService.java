package kr.co.metasoft.ito.api.common.service;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import kr.co.metasoft.ito.api.common.dto.VacationParamDto;
import kr.co.metasoft.ito.api.common.entity.VacationEntity;
import kr.co.metasoft.ito.api.common.mapper.VacationMapper;
import kr.co.metasoft.ito.api.common.repository.VacationRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

@Validated
@Service
public class VacationService {

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private VacationMapper vacationMapper;


    //getVacationList
    @Validated(value = {ReadValidationGroup.class})
    @Transactional
    public PageResponse<VacationEntity> getVacationList(
            @Valid VacationParamDto vacationParamDto,
            PageRequest pageRequest) {

        int totalRows = vacationMapper.selectVacationListCount(vacationParamDto);
        List<VacationEntity> vacationList = vacationMapper.selectVacationList(vacationParamDto, pageRequest);
        PageResponse<VacationEntity> pageResponse = new PageResponse<VacationEntity>(pageRequest, totalRows);
        pageResponse.setItems(vacationList);
        return pageResponse;
    }

    @Validated(value = {ReadValidationGroup.class})
    @Transactional
    public VacationEntity getVacation(
            @Valid @NotNull (groups = {ReadValidationGroup.class}) Long id) {
        return vacationMapper.selectVacation(id);
    }


    @Validated( value = {CreateValidationGroup.class})
    @Transactional
    public VacationEntity createVacation(
            @Valid @NotNull (groups = {CreateValidationGroup.class}) VacationEntity vacationEntity) {
        return vacationRepository.save(vacationEntity);
    }


    @Validated( value = {ModifyValidationGroup.class})
    @Transactional
    public VacationEntity modifyVacation(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) VacationEntity vacationEntity) {
        return vacationRepository.save(vacationEntity);
    }


    @Validated(value = {RemoveValidationGroup.class})
    @Transactional
    public void removeVacation(
           @Valid @NotNull (groups = {RemoveValidationGroup.class}) Long id) {
        vacationRepository.deleteById(id);
    }


}
