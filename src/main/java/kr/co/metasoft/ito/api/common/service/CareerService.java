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
import java.util.Optional;

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
        return careerCalc(careerEntity);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public CareerEntity modifyCareer(
            @Valid @NotNull(groups = {ModifyValidationGroup.class}) CareerEntity careerEntity) {
        return careerCalc(careerEntity);
    }

    @Transactional
    public void removeCareerList(List<Long> idList) { careerMapper.deleteCareerList(idList); }

    @Transactional
    public void removeCareer(Long personCareerId) { careerRepository.delete(CareerEntity.builder().personCareerId(personCareerId).build()); }

    // 경력 계산 함수
    public CareerEntity careerCalc(CareerEntity careerEntity) {
        Optional<CareerEntity> e = careerRepository.findById(careerEntity.getPersonId());
        Long beforeCareer = 0L;
        beforeCareer = ChronoUnit.DAYS.between(e.get().getStartPeriod(), e.get().getEndPeriod());
        System.out.println("============================================================");
        System.out.println(beforeCareer);
        System.out.println("============================================================");

        CareerEntity entity =  careerRepository.save(careerEntity);


//        CareerParamDto careerParamDto = CareerParamDto.builder()
//                .personId(careerEntity.getPersonId())
//                .build();
//
//        PageRequest pageRequest = new PageRequest();
//        pageRequest.setPageSize(10000000);
//        List<CareerEntity> careerList= getCareerList(careerParamDto, pageRequest).getItems();
//
//        Long career = 0L;
//        LocalDate startPeriod, endPeriod;
//        System.out.println("======================================================");
//        for(int i=0; i<careerList.size(); i++) {
//            if(entity.getPersonCareerId() == careerList.get(i).getPersonCareerId()) {
//                startPeriod = entity.getStartPeriod();
//                endPeriod = entity.getEndPeriod();
//                career += ChronoUnit.DAYS.between(startPeriod, endPeriod);
//                continue;
//            }
//
//            startPeriod =  careerList.get(i).getStartPeriod();
//            endPeriod = careerList.get(i).getEndPeriod();
//
//            career += ChronoUnit.DAYS.between(startPeriod, endPeriod);
//        }
//        System.out.println("Total Career: " + career);
//
//        // 경력 계산 코드
//        double result; // 계산된 경력 저장 변수
//        if(career/365 == 0) {
//            career /= 30;
//
//            if(career <= 9) result = career * 0.1;
//            else result = career * 0.01;
//        }
//        else {
//            double year = career / 365,
//                    month = career % 365 / 30;
//
//            if(month <= 9) month *= 0.1;
//            else month *= 0.01;
//
//            result = year + month;
//
//            System.out.println("Year: " + year + "     month: " + month);
//        }
//        System.out.println("Result: " + result);
//        System.out.println("======================================================");


        Long career = 0L;
        LocalDate startPeriod, endPeriod;
        Long personId = careerEntity.getPersonId();
        PersonEntity personEntity = personRepository.findById(personId).orElse(null);

        double result = Double.valueOf(personEntity.getCareer());
        System.out.println("===================================================================");
        startPeriod = careerEntity.getStartPeriod();
        endPeriod = careerEntity.getEndPeriod();
        career = ChronoUnit.DAYS.between(startPeriod, endPeriod);
        System.out.println("start: " + careerEntity.getStartPeriod());
        System.out.println("end: " + careerEntity.getEndPeriod());
        System.out.println("career: " + career);
        System.out.println("===================================================================");

        personRepository.save(personEntity);

        return entity;
    }
}