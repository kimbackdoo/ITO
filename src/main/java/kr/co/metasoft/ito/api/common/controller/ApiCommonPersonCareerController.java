package kr.co.metasoft.ito.api.common.controller;

import kr.co.metasoft.ito.api.common.dto.PersonCareerDto;
import kr.co.metasoft.ito.api.common.dto.PersonCareerParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonCareerEntity;

import kr.co.metasoft.ito.api.common.entity.id.PersonCareerEntityId;
import kr.co.metasoft.ito.api.common.service.PersonCareerService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/common/person-career")
public class ApiCommonPersonCareerController {
    @Autowired
    private PersonCareerService personCareerService;

    @GetMapping(path = "")
    public PageResponse<PersonCareerEntity> getPersonCareerList(
            @ModelAttribute PersonCareerParamDto personCareerParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return personCareerService.getPersonCareerList(personCareerParamDto, pageRequest);
    }

    @GetMapping(path = "{personId},{careerId}")
    public PersonCareerEntity getPersonCareer(
            @PathVariable(name = "personId") Long personId,
            @PathVariable(name = "careerId") Long careerId) {
        return personCareerService.getPersonCareer(personId, careerId);
    }

    @PostMapping(path = "", params = {"bulk"})
    public List<PersonCareerEntity> createPersonCareerList(
            @RequestBody List<PersonCareerDto> personCareerDtoList) {
        List<PersonCareerEntity> personCareerList = new ArrayList<>();
        for(int i=0; i<personCareerList.size(); i++) {
            PersonCareerDto personCareerDto = personCareerDtoList.get(i);
            PersonCareerEntity personCareerEntity = PersonCareerEntity.builder()
                    .personId(personCareerDto.getPersonId())
                    .careerId(personCareerDto.getCareerId())
                    .build();
            personCareerList.add(personCareerEntity);
        }
        return personCareerService.createPersonCareerList(personCareerList);
    }

    @PostMapping(path = "", params = {"!bulk"})
    public PersonCareerEntity createPersonCareer (
            @RequestBody PersonCareerDto personCareerDto) {
        PersonCareerEntity personCareerEntity = PersonCareerEntity.builder()
                .personId(personCareerDto.getPersonId())
                .careerId(personCareerDto.getCareerId())
                .build();
        return personCareerService.createPersonCareer(personCareerEntity);
    }

    @PutMapping(path = "")
    public List<PersonCareerEntity> modifyPersonCareerList(
            @RequestBody List<PersonCareerDto> personCareerDtoList) {
        List<PersonCareerEntity> personCareerList = new ArrayList<>();
        for(int i=0; i<personCareerDtoList.size(); i++) {
            PersonCareerDto personCareerDto = personCareerDtoList.get(i);
            PersonCareerEntity personCareerEntity = PersonCareerEntity.builder()
                    .personId(personCareerDto.getPersonId())
                    .careerId(personCareerDto.getCareerId())
                    .build();
            personCareerList.add(personCareerEntity);
        }
        return personCareerService.modifyPersonCareerList(personCareerList);
    }

    @PutMapping(path = "{personId}")
    public PersonCareerEntity modifyPersonCareer(
            @PathVariable(name = "personId") Long personId,
            @PathVariable(name = "careerId") Long careerId,
            @RequestBody PersonCareerDto personCareerDto) {
        PersonCareerEntity personCareerEntity = PersonCareerEntity.builder()
                .personId(personId)
                .careerId(careerId)
                .build();
        return personCareerService.modifyPersonCareer(personCareerEntity);
    }

    @DeleteMapping(path = "")
    public void removePersonCareerList(
            @RequestBody List<PersonCareerEntityId> personCareerEntityIdList) {
        personCareerService.removePersonCareerList(personCareerEntityIdList);
    }

    @DeleteMapping(path = "{personId},{careerId}")
    public void removePersonCareer(
            @PathVariable(name = "personId") Long personId,
            @PathVariable(name = "careerId") Long careerId) {
        personCareerService.removePersonCareer(personId, careerId);
    }
}
