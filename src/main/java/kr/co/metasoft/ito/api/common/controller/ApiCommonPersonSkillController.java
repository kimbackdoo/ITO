package kr.co.metasoft.ito.api.common.controller;

import java.util.ArrayList;
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

import kr.co.metasoft.ito.api.common.dto.PersonSkillDto;
import kr.co.metasoft.ito.api.common.dto.PersonSkillParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonSkillEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonSkillEntityId;
import kr.co.metasoft.ito.api.common.service.PersonSkillService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@RestController
@RequestMapping (path = "api/common/person-skills")
public class ApiCommonPersonSkillController {

    @Autowired
    private PersonSkillService personSkillService;

    @GetMapping (path = "")
    public PageResponse<PersonSkillEntity> getPersonSkillList(
            @ModelAttribute PersonSkillParamDto personSkillParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return personSkillService.getPersonSkillList(personSkillParamDto, pageRequest);
    }

    @GetMapping (path = "{personId},{skill}")
    public PersonSkillEntity getPersonSkill(
            @PathVariable (name = "personId") Long personId,
            @PathVariable (name = "skill") String skill) {
        return personSkillService.getPersonSkill(personId, skill);
    }

    @PostMapping (path = "", params = {"bulk"})
    public List<PersonSkillEntity> createPersonSkillList(
            @RequestBody List<PersonSkillDto> personSkillDtoList) {
        List<PersonSkillEntity> personSkillList = new ArrayList<>();
        for (int i = 0; i < personSkillDtoList.size(); i++) {
            PersonSkillDto personSkillDto = personSkillDtoList.get(i);
            PersonSkillEntity personSkillEntity = PersonSkillEntity.builder()
                    .personId(personSkillDto.getPersonId())
                    .skill(personSkillDto.getSkill())
                    .build();
            personSkillList.add(personSkillEntity);
        }
        return personSkillService.createPersonSkillList(personSkillList);
    }

    @PostMapping (path = "", params = {"!bulk"})
    public PersonSkillEntity createPersonSkill(
            @RequestBody PersonSkillDto personSkillDto) {
        PersonSkillEntity personSkillEntity = PersonSkillEntity.builder()
                .personId(personSkillDto.getPersonId())
                .skill(personSkillDto.getSkill())
                .build();
        return personSkillService.createPersonSkill(personSkillEntity);
    }

    @PutMapping (path = "")
    public List<PersonSkillEntity> modifyPersonSkillList(
            @RequestBody List<PersonSkillDto> personSkillDtoList) {
        List<PersonSkillEntity> personSkillList = new ArrayList<>();
        for (int i = 0; i < personSkillDtoList.size(); i++) {
            PersonSkillDto personSkillDto = personSkillDtoList.get(i);
            PersonSkillEntity personSkillEntity = PersonSkillEntity.builder()
                    .personId(personSkillDto.getPersonId())
                    .skill(personSkillDto.getSkill())
                    .build();
            personSkillList.add(personSkillEntity);
        }
        return personSkillService.modifyPersonSkillList(personSkillList);
    }

    @PutMapping (path = "{personId},{skill}")
    public PersonSkillEntity modifyPersonSkill(
            @PathVariable (name = "personId") Long personId,
            @PathVariable (name = "skill") String skill,
            @RequestBody PersonSkillDto personSkillDto) {
        PersonSkillEntity personSkillEntity = PersonSkillEntity.builder()
                .personId(personId)
                .skill(skill)
                .build();
        return personSkillService.modifyPersonSkill(personSkillEntity);
    }

    @DeleteMapping (path = "")
    public void removePersonSkillList(
            @RequestBody List<PersonSkillEntityId> personSkillEntityIdList) {
        personSkillService.removePersonSkillList(personSkillEntityIdList);
    }

    @DeleteMapping (path = "{personId},{skill}")
    public void removePersonSkill(
            @PathVariable (name = "personId") Long personId,
            @PathVariable (name = "skill") String skill) {
        personSkillService.removePersonSkill(personId, skill);
    }

}