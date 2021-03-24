package kr.co.metasoft.ito.api.common.service;

import kr.co.metasoft.ito.api.common.dto.PersonCareerParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonCareerEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonCareerEntityId;
import kr.co.metasoft.ito.api.common.mapper.PersonCareerMapper;
import kr.co.metasoft.ito.api.common.repository.PersonCareerRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Validated
@Service
public class PersonCareerService {
    @Autowired
    private PersonCareerRepository personCareerRepository;

    @Autowired
    private PersonCareerMapper personCareerMapper;

    @Validated(value = {ReadValidationGroup.class})
    @Transactional(readOnly = true)
    public PageResponse<PersonCareerEntity> getPersonCareerList(
            @Valid PersonCareerParamDto personCareerParamDto,
            PageRequest pageRequest) {
        Integer personCareerListCount = personCareerMapper.selectPersonCareerListCount(personCareerParamDto);
        List<PersonCareerEntity> personCareerList = personCareerMapper.selectPersonCareerList(personCareerParamDto, pageRequest);
        PageResponse<PersonCareerEntity> pageResponse = new PageResponse<>(pageRequest, personCareerListCount);
        pageResponse.setItems(personCareerList);
        return pageResponse;
    }

    @Validated(value = {ReadValidationGroup.class})
    @Transactional(readOnly = true)
    public PersonCareerEntity getPersonCareer(
            @Valid @NotNull(groups = {ReadValidationGroup.class}) Long personId,
            @Valid @NotNull(groups = {ReadValidationGroup.class}) Long careerId) {
        return personCareerMapper.selectPersonCareer(personId, careerId);
    }

    @Validated(value = {CreateValidationGroup.class})
    @Transactional
    public List<PersonCareerEntity> createPersonCareerList(
            @Valid @NotEmpty(groups = {CreateValidationGroup.class}) List<@NotNull (groups = {CreateValidationGroup.class}) PersonCareerEntity> personCareerList) {
        return personCareerRepository.saveAll(personCareerList);
    }

    @Validated(value = {CreateValidationGroup.class})
    @Transactional
    public PersonCareerEntity createPersonCareer(
            @Valid @NotNull (groups = {CreateValidationGroup.class}) PersonCareerEntity personCareerEntity) {
        return personCareerRepository.save(personCareerEntity);
    }

    @Validated(value = {ModifyValidationGroup.class})
    @Transactional
    public List<PersonCareerEntity> modifyPersonCareerList(
            @Valid @NotEmpty(groups = {ModifyValidationGroup.class}) List<@NotNull(groups = {ModifyValidationGroup.class}) PersonCareerEntity> personCareerList) {
        return personCareerRepository.saveAll(personCareerList);
    }

    @Validated(value = {ModifyValidationGroup.class})
    @Transactional
    public PersonCareerEntity modifyPersonCareer(
            @Valid @NotNull(groups = {ModifyValidationGroup.class}) PersonCareerEntity personCareerEntity) {
        return personCareerRepository.save(personCareerEntity);
    }

    @Validated(value = {RemoveValidationGroup.class})
    @Transactional
    public void removePersonCareerList(
            @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) PersonCareerEntityId> personCareerEntityIdList) {
        List<PersonCareerEntity> personCareerList = new ArrayList<>();
        for(int i=0; i<personCareerEntityIdList.size(); i++) {
            PersonCareerEntityId personCareerEntityId = personCareerEntityIdList.get(i);
            Long personId = personCareerEntityId.getPersonId();
            Long careerId = personCareerEntityId.getCareerId();
            personCareerList.add(PersonCareerEntity.builder().personId(personId).careerId(careerId).build());
        }
        personCareerRepository.deleteAll(personCareerList);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removePersonCareer(
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) Long personId,
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) Long careerId) {
        personCareerRepository.delete(PersonCareerEntity.builder().personId(personId).careerId(careerId).build());
    }
}
