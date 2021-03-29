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

import kr.co.metasoft.ito.api.common.dto.PersonLanguageParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonLanguageEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonLanguageEntityId;
import kr.co.metasoft.ito.api.common.mapper.PersonLanguageMapper;
import kr.co.metasoft.ito.api.common.repository.PersonLanguageRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

@Validated
@Service
public class PersonLanguageService {

    @Autowired
    private PersonLanguageRepository personLanguageRepository;

    @Autowired
    private PersonLanguageMapper personLanguageMapper;

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public PageResponse<PersonLanguageEntity> getPersonLanguageList(
            @Valid PersonLanguageParamDto personLanguageParamDto,
            PageRequest pageRequest) {
        Integer personLanguageListCount = personLanguageMapper.selectPersonLanguageListCount(personLanguageParamDto);
        List<PersonLanguageEntity> personLanguageList = personLanguageMapper.selectPersonLanguageList(personLanguageParamDto, pageRequest);
        PageResponse<PersonLanguageEntity> pageResponse = new PageResponse<>(pageRequest, personLanguageListCount);
        pageResponse.setItems(personLanguageList);
        return pageResponse;
    }

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public PersonLanguageEntity getPersonLanguage(
            @Valid @NotNull (groups = {ReadValidationGroup.class}) Long personId,
            @Valid @NotNull (groups = {ReadValidationGroup.class}) String language) {
        return personLanguageMapper.selectPersonLanguage(personId, language);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public List<PersonLanguageEntity> createPersonLanguageList(
            @Valid @NotEmpty (groups = {CreateValidationGroup.class}) List<@NotNull (groups = {CreateValidationGroup.class}) PersonLanguageEntity> personLanguageList) {
        return personLanguageRepository.saveAll(personLanguageList);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public PersonLanguageEntity createPersonLanguage(
            @Valid @NotNull (groups = {CreateValidationGroup.class}) PersonLanguageEntity personLanguageEntity) {
        return personLanguageRepository.save(personLanguageEntity);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public List<PersonLanguageEntity> modifyPersonLanguageList(
            @Valid @NotEmpty (groups = {ModifyValidationGroup.class}) List<@NotNull (groups = {ModifyValidationGroup.class}) PersonLanguageEntity> personLanguageList) {
        return personLanguageRepository.saveAll(personLanguageList);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public PersonLanguageEntity modifyPersonLanguage(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) PersonLanguageEntity personLanguageEntity) {
        return personLanguageRepository.save(personLanguageEntity);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removePersonLanguageList(
            @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) PersonLanguageEntityId> personLanguageEntityIdList) {
        List<PersonLanguageEntity> personLanguageList = new ArrayList<>();
        for (int i = 0; i < personLanguageEntityIdList.size(); i++) {
            PersonLanguageEntityId personLanguageEntityId = personLanguageEntityIdList.get(i);
            Long personId = personLanguageEntityId.getPersonId();
            String language = personLanguageEntityId.getLanguage();
            personLanguageList.add(PersonLanguageEntity.builder().personId(personId).language(language).build());
        }
        personLanguageRepository.deleteAll(personLanguageList);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removePersonLanguage(
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) Long personId,
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) String language) {
        personLanguageRepository.delete(PersonLanguageEntity.builder().personId(personId).language(language).build());
    }

}