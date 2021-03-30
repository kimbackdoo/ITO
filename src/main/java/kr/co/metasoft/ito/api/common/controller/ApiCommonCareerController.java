package kr.co.metasoft.ito.api.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.common.dto.CareerParamDto;
import kr.co.metasoft.ito.api.common.entity.CareerEntity;
import kr.co.metasoft.ito.api.common.service.CareerService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@RestController
@RequestMapping(path = "api/common/career")
public class ApiCommonCareerController {
    @Autowired
    private CareerService careerService;

    @GetMapping(path = "")
    public PageResponse<CareerEntity> getCareerList(
            @ModelAttribute CareerParamDto careerParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return careerService.getCareerList(careerParamDto, pageRequest);
    }

    @GetMapping(path = "{personCareerId}")
    public CareerEntity getProfile(
            @PathVariable(name = "personCareerId") Long personCareerId) {
        return careerService.getCareer(personCareerId);
    }

    @PostMapping(path = "")
    public CareerEntity createCareer(
            @RequestBody CareerEntity careerEntity) {
        return careerService.createCareer(careerEntity);
    }

    @PutMapping(path = "{personCareerId}")
    public CareerEntity modifyCareer(
            @PathVariable(name = "personCareerId") Long personCareerId,
            @RequestBody CareerEntity careerEntity) {
        careerEntity.setPersonCareerId(personCareerId);
        return careerService.modifyCareer(careerEntity);
    }

    @DeleteMapping(path = "")
    public void removeCareerList(
            @RequestBody List<Long> idList) {
        careerService.removeCareerList(idList);
    }

    @DeleteMapping(path = "{personCareerId}")
    public void removeCareer(
            @PathVariable(name = "personCareerId") Long personCareerId) {
        careerService.removeCareer(personCareerId);
    }
}
