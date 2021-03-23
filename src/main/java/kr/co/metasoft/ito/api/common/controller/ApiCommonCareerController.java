package kr.co.metasoft.ito.api.common.controller;

import kr.co.metasoft.ito.api.common.dto.CareerDto;
import kr.co.metasoft.ito.api.common.entity.CareerEntity;
import kr.co.metasoft.ito.api.common.service.CareerService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/common/career")
public class ApiCommonCareerController {
    @Autowired
    private CareerService careerService;

    @GetMapping(path = "")
    public PageResponse<CareerEntity> getCareerList(
            @ModelAttribute CareerDto careerDto,
            @ModelAttribute PageRequest pageRequest) {
        return careerService.getCareerList(careerDto, pageRequest);
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
