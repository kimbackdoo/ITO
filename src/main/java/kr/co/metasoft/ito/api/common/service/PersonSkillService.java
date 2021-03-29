package kr.co.metasoft.ito.api.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import kr.co.metasoft.ito.api.common.dto.PersonSkillParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonSkillEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonSkillEntityId;
import kr.co.metasoft.ito.api.common.mapper.PersonSkillMapper;
import kr.co.metasoft.ito.api.common.repository.PersonSkillRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

@Validated
@Service
public class PersonSkillService {

    @Autowired
    private PersonSkillRepository personSkillRepository;

    @Autowired
    private PersonSkillMapper personSkillMapper;

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public PageResponse<PersonSkillEntity> getPersonSkillList(
            @Valid PersonSkillParamDto personSkillParamDto,
            PageRequest pageRequest) {
        Integer personSkillListCount = personSkillMapper.selectPersonSkillListCount(personSkillParamDto);
        List<PersonSkillEntity> personSkillList = personSkillMapper.selectPersonSkillList(personSkillParamDto, pageRequest);
        PageResponse<PersonSkillEntity> pageResponse = new PageResponse<>(pageRequest, personSkillListCount);
        pageResponse.setItems(personSkillList);
        return pageResponse;
    }

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public PersonSkillEntity getPersonSkill(
            @Valid @NotNull (groups = {ReadValidationGroup.class}) Long personId,
            @Valid @NotNull (groups = {ReadValidationGroup.class}) String skill) {
        return personSkillMapper.selectPersonSkill(personId, skill);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public List<PersonSkillEntity> createPersonSkillList(
            @Valid @NotEmpty (groups = {CreateValidationGroup.class}) List<@NotNull (groups = {CreateValidationGroup.class}) PersonSkillEntity> personSkillList) {
        return personSkillRepository.saveAll(personSkillList);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public PersonSkillEntity createPersonSkill(
            @Valid @NotNull (groups = {CreateValidationGroup.class}) PersonSkillEntity personSkillEntity) {
        return personSkillRepository.save(personSkillEntity);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public List<PersonSkillEntity> modifyPersonSkillList(
            @Valid @NotEmpty (groups = {ModifyValidationGroup.class}) List<@NotNull (groups = {ModifyValidationGroup.class}) PersonSkillEntity> personSkillList) {
        return personSkillRepository.saveAll(personSkillList);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public PersonSkillEntity modifyPersonSkill(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) PersonSkillEntity personSkillEntity) {
        return personSkillRepository.save(personSkillEntity);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removePersonSkillList(
            @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) PersonSkillEntityId> personSkillEntityIdList) {
        List<PersonSkillEntity> personSkillList = new ArrayList<>();
        for (int i = 0; i < personSkillEntityIdList.size(); i++) {
            PersonSkillEntityId personSkillEntityId = personSkillEntityIdList.get(i);
            Long personId = personSkillEntityId.getPersonId();
            String skill = personSkillEntityId.getSkill();
            personSkillList.add(PersonSkillEntity.builder().personId(personId).skill(skill).build());
        }
        personSkillRepository.deleteAll(personSkillList);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removePersonSkill(
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) Long personId,
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) String skill) {
        personSkillRepository.delete(PersonSkillEntity.builder().personId(personId).skill(skill).build());
    }

}