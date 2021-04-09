package kr.co.metasoft.ito.api.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import kr.co.metasoft.ito.api.common.dto.PersonParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.mapper.PersonMapper;
import kr.co.metasoft.ito.api.common.repository.PersonRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

@Validated
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public PageResponse<PersonEntity> getPersonList(
            @Valid PersonParamDto personParamDto,
            PageRequest pageRequest) {
        System.out.println("===========================================================================");
        System.out.println(personParamDto);
        System.out.println("Name: " + personParamDto.getName());
        System.out.println("JobType: " + personParamDto.getJobType());
        if(personParamDto.getSkillListLike() == null) {
            System.out. println("Skill: null");
        }
        else {
            System.out.print("Skill: ");
            for(String skill : personParamDto.getSkillListLike()) {
                System.out.print(skill + ", ");
            }
            System.out.println();
        }
        System.out.println("Local: " + personParamDto.getLocal());
        System.out.println("DetailLocal: " + personParamDto.getDetailLocal());
        System.out.println("Career: " + personParamDto.getCareer());
        System.out.println("Education: " + personParamDto.getEducation());
        System.out.println("StartBirthDate: " + personParamDto.getStartBirthDate());
        System.out.println("EndBirthDate: " + personParamDto.getEndBirthDate());
        System.out.println("WorkableDay: " + personParamDto.getWorkableDay());
        System.out.println("RatingScore: " + personParamDto.getRatingScore());
        System.out.println("===========================================================================");

        Integer personListCount = personMapper.selectPersonListCount(personParamDto);
        List<PersonEntity> personList = personMapper.selectPersonList(personParamDto, pageRequest);
        PageResponse<PersonEntity> pageResponse = new PageResponse<>(pageRequest, personListCount);
        pageResponse.setItems(personList);
        return pageResponse;
    }

    @Validated (value = {ReadValidationGroup.class})
    @Transactional
    public PersonEntity getPerson(
            @Valid @NotNull (groups = {ReadValidationGroup.class}) Long id) {
        return personMapper.selectPerson(id);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public List<PersonEntity> createPersonList(
            @Valid @NotEmpty (groups = {CreateValidationGroup.class}) List<@NotNull (groups = {CreateValidationGroup.class}) PersonEntity> personList) {
        return personRepository.saveAll(personList);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public PersonEntity createPerson(
            @Valid @NotNull (groups = {CreateValidationGroup.class}) PersonEntity personEntity) {
        return personRepository.save(personEntity);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public List<PersonEntity> modifyPersonList(
            @Valid @NotEmpty (groups = {ModifyValidationGroup.class}) List<@NotNull (groups = {ModifyValidationGroup.class}) PersonEntity> personList) {
        return personRepository.saveAll(personList);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public PersonEntity modifyPerson(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) PersonEntity personEntity) {
        return personRepository.save(personEntity);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removePersonList(
            @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) Long> idList) {
        List<PersonEntity> personList = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            Long id = idList.get(i);
            personList.add(PersonEntity.builder().id(id).build());
        }
        personRepository.deleteAll(personList);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removePerson(
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) Long id) {
        personRepository.delete(PersonEntity.builder().id(id).build());
    }
}