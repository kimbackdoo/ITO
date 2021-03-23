package kr.co.metasoft.ito.api.common.service;

import kr.co.metasoft.ito.api.common.dto.CareerDto;
import kr.co.metasoft.ito.api.common.entity.CareerEntity;
import kr.co.metasoft.ito.api.common.mapper.CareerMapper;
import kr.co.metasoft.ito.api.common.repository.CareerRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
@Service
public class CareerService {
    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private CareerMapper careerMapper;

    @Transactional
    public PageResponse<CareerEntity> getCareerList(
            @Valid CareerDto careerDto,
            PageRequest pageRequest) {
        Integer careerListCount = careerMapper.selectCareerListCount(careerDto);
        List<CareerEntity> careerList = careerMapper.selectCareerList(careerDto, pageRequest);
        PageResponse<CareerEntity> pageResponse = new PageResponse<>(pageRequest, careerListCount);
        pageResponse.setItems(careerList);

        return pageResponse;
    }

    @Transactional
    public CareerEntity getCareer(Long personCareerId) {
        return careerRepository.findById(personCareerId).orElse(null);
    }

    @Transactional
    public CareerEntity createCareer(CareerEntity careerEntity) {
        return careerRepository.save(careerEntity);
    }

    @Transactional
    public CareerEntity modifyCareer(CareerEntity careerEntity) {
        return careerRepository.save(careerEntity);
    }

    @Transactional
    public void removeCareerList(List<Long> idList) { careerMapper.deleteCareerList(idList); }

    @Transactional
    public void removeCareer(Long personCareerId) { careerRepository.delete(CareerEntity.builder().personCareerId(personCareerId).build()); }
}