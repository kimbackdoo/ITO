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

import kr.co.metasoft.ito.api.common.dto.PersonSectorParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonSectorEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonSectorEntityId;
import kr.co.metasoft.ito.api.common.mapper.PersonSectorMapper;
import kr.co.metasoft.ito.api.common.repository.PersonSectorRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

@Validated
@Service
public class PersonSectorService {

    @Autowired
    private PersonSectorRepository personSectorRepository;

    @Autowired
    private PersonSectorMapper personSectorMapper;

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public PageResponse<PersonSectorEntity> getPersonSectorList(
            @Valid PersonSectorParamDto personSectorParamDto,
            PageRequest pageRequest) {
        Integer personSectorListCount = personSectorMapper.selectPersonSectorListCount(personSectorParamDto);
        List<PersonSectorEntity> personSectorList = personSectorMapper.selectPersonSectorList(personSectorParamDto, pageRequest);
        PageResponse<PersonSectorEntity> pageResponse = new PageResponse<>(pageRequest, personSectorListCount);
        pageResponse.setItems(personSectorList);
        return pageResponse;
    }

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public PersonSectorEntity getPersonSector(
            @Valid @NotNull (groups = {ReadValidationGroup.class}) Long personId,
            @Valid @NotNull (groups = {ReadValidationGroup.class}) String sector) {
        return personSectorMapper.selectPersonSector(personId, sector);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public List<PersonSectorEntity> createPersonSectorList(
            @Valid @NotEmpty (groups = {CreateValidationGroup.class}) List<@NotNull (groups = {CreateValidationGroup.class}) PersonSectorEntity> personSectorList) {
        return personSectorRepository.saveAll(personSectorList);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public PersonSectorEntity createPersonSector(
            @Valid @NotNull (groups = {CreateValidationGroup.class}) PersonSectorEntity personSectorEntity) {
        return personSectorRepository.save(personSectorEntity);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public List<PersonSectorEntity> modifyPersonSectorList(
            @Valid @NotEmpty (groups = {ModifyValidationGroup.class}) List<@NotNull (groups = {ModifyValidationGroup.class}) PersonSectorEntity> personSectorList) {
        return personSectorRepository.saveAll(personSectorList);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public PersonSectorEntity modifyPersonSector(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) PersonSectorEntity personSectorEntity) {
        return personSectorRepository.save(personSectorEntity);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removePersonSectorList(
            @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) PersonSectorEntityId> personSectorEntityIdList) {
        List<PersonSectorEntity> personSectorList = new ArrayList<>();
        for (int i = 0; i < personSectorEntityIdList.size(); i++) {
            PersonSectorEntityId personSectorEntityId = personSectorEntityIdList.get(i);
            Long personId = personSectorEntityId.getPersonId();
            String sector = personSectorEntityId.getSector();
            personSectorList.add(PersonSectorEntity.builder().personId(personId).sector(sector).build());
        }
        personSectorRepository.deleteAll(personSectorList);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removePersonSector(
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) Long personId,
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) String sector) {
        personSectorRepository.delete(PersonSectorEntity.builder().personId(personId).sector(sector).build());
    }

}