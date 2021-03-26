package kr.co.metasoft.ito.api.common.service;

import kr.co.metasoft.ito.api.common.dto.CareerParamDto;
import kr.co.metasoft.ito.api.common.entity.CareerEntity;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.mapper.CareerMapper;
import kr.co.metasoft.ito.api.common.repository.CareerRepository;
import kr.co.metasoft.ito.api.common.repository.PersonRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Validated
@Service
public class CareerService {
    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private CareerMapper careerMapper;

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public PageResponse<CareerEntity> getCareerList(
            @Valid CareerParamDto careerParamDto,
            PageRequest pageRequest) {
        Integer careerListCount = careerMapper.selectCareerListCount(careerParamDto);
        List<CareerEntity> careerList = careerMapper.selectCareerList(careerParamDto, pageRequest);
        PageResponse<CareerEntity> pageResponse = new PageResponse<>(pageRequest, careerListCount);
        pageResponse.setItems(careerList);

        return pageResponse;
    }

    @Transactional
    public CareerEntity getCareer(Long personCareerId) {
        return careerRepository.findById(personCareerId).orElse(null);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public CareerEntity createCareer(CareerEntity careerEntity) {
        return careerRepository.save(careerEntity);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public CareerEntity modifyCareer(
            @Valid @NotNull(groups = {ModifyValidationGroup.class}) CareerEntity careerEntity) {
        CareerEntity entity =  careerRepository.save(careerEntity);

        CareerParamDto careerParamDto = CareerParamDto.builder()
                .personId(careerEntity.getPersonId())
                .build();

        System.out.println(careerParamDto);

        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(10000000);
        List<CareerEntity> careerList= getCareerList(careerParamDto, pageRequest).getItems();

        int career = 0;
        for(int i=0; i<careerList.size(); i++) {
            LocalDate startPeriod =  careerList.get(i).getStartPeriod();
            LocalDate endPeriod = careerList.get(i).getEndPeriod();

            career += ChronoUnit.DAYS.between(startPeriod, endPeriod);
        }

        System.out.println("=============" + career + "=================");

        Long personId = careerEntity.getPersonId();
        PersonEntity personEntity = personRepository.findById(personId).orElse(null);
        personEntity.setCareer(career + "");
        personRepository.save(personEntity);

        return entity;
    }

    @Transactional
    public void removeCareerList(List<Long> idList) { careerMapper.deleteCareerList(idList); }

    @Transactional
    public void removeCareer(Long personCareerId) { careerRepository.delete(CareerEntity.builder().personCareerId(personCareerId).build()); }
}