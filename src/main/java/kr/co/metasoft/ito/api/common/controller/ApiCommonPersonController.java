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

import kr.co.metasoft.ito.api.common.dto.PersonDto;
import kr.co.metasoft.ito.api.common.dto.PersonParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.service.PersonService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@RestController
@RequestMapping (path = "api/common/people")
public class ApiCommonPersonController {

    @Autowired
    private PersonService personService;

    @GetMapping (path = "")
    public PageResponse<PersonEntity> getPersonList(
            @ModelAttribute PersonParamDto personParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return personService.getPersonList(personParamDto, pageRequest);
    }

    @GetMapping (path = "{id}")
    public PersonEntity getPerson(
            @PathVariable (name = "id") Long id) {
        return personService.getPerson(id);
    }

    @PostMapping (path = "", params = {"bulk"})
    public List<PersonEntity> createPersonList(
            @RequestBody List<PersonDto> personDtoList) {
        List<PersonEntity> personList = new ArrayList<>();
        for (int i = 0; i < personDtoList.size(); i++) {
            PersonDto personDto = personDtoList.get(i);
            PersonEntity personEntity = PersonEntity.builder()
                    .name(personDto.getName())
                    .gender(personDto.getGender())
                    .actualPay(personDto.getActualPay())
                    .ratingScore(personDto.getRatingScore())
                    .phoneNumber(personDto.getPhoneNumber())
                    .jobType(personDto.getJobType())
                    .inputStatus(personDto.getInputStatus())
                    .certificateStatus(personDto.getCertificateStatus())
                    .skill(personDto.getSkill())
                    .role(personDto.getRole())
                    .career(personDto.getCareer())
                    .minPay(personDto.getMinPay())
                    .maxPay(personDto.getMaxPay())
                    .local(personDto.getLocal())
                    .detailLocal(personDto.getDetailLocal())
                    .workableDay(personDto.getWorkableDay())
                    .postcode(personDto.getPostcode())
                    .address(personDto.getAddress())
                    .detailAddress(personDto.getDetailAddress())
                    .email(personDto.getEmail())
                    .website(personDto.getWebsite())
                    .education(personDto.getEducation())
                    .birthDate(personDto.getBirthDate())
                    .memo(personDto.getMemo())
                    .days(personDto.getDays())
                    .build();
            personList.add(personEntity);
        }
        return personService.createPersonList(personList);
    }

    @PostMapping (path = "", params = {"!bulk"})
    public PersonEntity createPerson(
            @RequestBody PersonDto personDto) {
        PersonEntity personEntity = PersonEntity.builder()
                .name(personDto.getName())
                .gender(personDto.getGender())
                .actualPay(personDto.getActualPay())
                .ratingScore(personDto.getRatingScore())
                .phoneNumber(personDto.getPhoneNumber())
                .jobType(personDto.getJobType())
                .skill(personDto.getSkill())
                .inputStatus(personDto.getInputStatus())
                .certificateStatus(personDto.getCertificateStatus())
                .role(personDto.getRole())
                .career(personDto.getCareer())
                .minPay(personDto.getMinPay())
                .maxPay(personDto.getMaxPay())
                .local(personDto.getLocal())
                .detailLocal(personDto.getDetailLocal())
                .workableDay(personDto.getWorkableDay())
                .postcode(personDto.getPostcode())
                .address(personDto.getAddress())
                .detailAddress(personDto.getDetailAddress())
                .email(personDto.getEmail())
                .website(personDto.getWebsite())
                .education(personDto.getEducation())
                .birthDate(personDto.getBirthDate())
                .memo(personDto.getMemo())
                .days(personDto.getDays())
                .build();
        return personService.createPerson(personEntity);
    }

    @PutMapping (path = "")
    public List<PersonEntity> modifyPersonList(
            @RequestBody List<PersonDto> personDtoList) {
        List<PersonEntity> personList = new ArrayList<>();
        for (int i = 0; i < personDtoList.size(); i++) {
            PersonDto personDto = personDtoList.get(i);
            PersonEntity personEntity = PersonEntity.builder()
                    .id(personDto.getId())
                    .name(personDto.getName())
                    .gender(personDto.getGender())
                    .actualPay(personDto.getActualPay())
                    .ratingScore(personDto.getRatingScore())
                    .phoneNumber(personDto.getPhoneNumber())
                    .jobType(personDto.getJobType())
                    .skill(personDto.getSkill())
                    .inputStatus(personDto.getInputStatus())
                    .certificateStatus(personDto.getCertificateStatus())
                    .role(personDto.getRole())
                    .career(personDto.getCareer())
                    .minPay(personDto.getMinPay())
                    .maxPay(personDto.getMaxPay())
                    .local(personDto.getLocal())
                    .detailLocal(personDto.getDetailLocal())
                    .workableDay(personDto.getWorkableDay())
                    .postcode(personDto.getPostcode())
                    .address(personDto.getAddress())
                    .detailAddress(personDto.getDetailAddress())
                    .email(personDto.getEmail())
                    .website(personDto.getWebsite())
                    .education(personDto.getEducation())
                    .birthDate(personDto.getBirthDate())
                    .memo(personDto.getMemo())
                    .days(personDto.getDays())
                    .build();
            personList.add(personEntity);
        }
        return personService.modifyPersonList(personList);
    }

    @PutMapping (path = "{id}")
    public PersonEntity modifyPerson(
            @PathVariable (name = "id") Long id,
            @RequestBody PersonDto personDto) {
        PersonEntity personEntity = PersonEntity.builder()
                .id(id)
                .name(personDto.getName())
                .gender(personDto.getGender())
                .actualPay(personDto.getActualPay())
                .ratingScore(personDto.getRatingScore())
                .phoneNumber(personDto.getPhoneNumber())
                .jobType(personDto.getJobType())
                .skill(personDto.getSkill())
                .inputStatus(personDto.getInputStatus())
                .certificateStatus(personDto.getCertificateStatus())
                .role(personDto.getRole())
                .career(personDto.getCareer())
                .minPay(personDto.getMinPay())
                .maxPay(personDto.getMaxPay())
                .local(personDto.getLocal())
                .detailLocal(personDto.getDetailLocal())
                .workableDay(personDto.getWorkableDay())
                .postcode(personDto.getPostcode())
                .address(personDto.getAddress())
                .detailAddress(personDto.getDetailAddress())
                .email(personDto.getEmail())
                .website(personDto.getWebsite())
                .education(personDto.getEducation())
                .birthDate(personDto.getBirthDate())
                .status(personDto.getStatus())
                .memo(personDto.getMemo())
                .days(personDto.getDays())
                .build();
        return personService.modifyPerson(personEntity);
    }

    @DeleteMapping (path = "")
    public void removePersonList(
            @RequestBody List<Long> idList) {
        personService.removePersonList(idList);
    }

    @DeleteMapping (path = "{id}")
    public void removePerson(
            @PathVariable (name = "id") Long id) {
        personService.removePerson(id);
    }
}