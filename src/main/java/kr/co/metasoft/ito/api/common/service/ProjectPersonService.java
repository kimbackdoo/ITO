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


import kr.co.metasoft.ito.api.common.dto.ProjectPersonParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.entity.PersonLanguageEntity;
import kr.co.metasoft.ito.api.common.entity.ProjectPersonEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonLanguageEntityId;
import kr.co.metasoft.ito.api.common.entity.id.ProjectPersonEntityId;
import kr.co.metasoft.ito.api.common.mapper.ProjectPersonMapper;
import kr.co.metasoft.ito.api.common.repository.ProjectPersonRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;



@Validated
@Service
public class ProjectPersonService {


    @Autowired
    private ProjectPersonRepository projectPersonRepository;

    @Autowired
    private ProjectPersonMapper projectPersonMapper;

    @Validated(ReadValidationGroup.class)
    @Transactional (readOnly = true)
    public PageResponse<ProjectPersonEntity> getprojectPersonList(
            @Valid ProjectPersonParamDto projectPersonParamDto,
            PageRequest pageRequest){
        Integer ProjectPersonListCount = projectPersonMapper.selectProjectPersonListCount(projectPersonParamDto);
        List<ProjectPersonEntity> projectPersonList = projectPersonMapper.selectProjectPersonList(projectPersonParamDto, pageRequest);
        PageResponse<ProjectPersonEntity> pageResponse = new PageResponse<>(pageRequest, ProjectPersonListCount);
        pageResponse.setItems(projectPersonList);
        return pageResponse;
    }


    @Validated(CreateValidationGroup.class)
    @Transactional
    public ProjectPersonEntity createProjectPerson(
            @Valid @NotNull (groups= {CreateValidationGroup.class}) ProjectPersonEntity projectPersonEntity) {
        return projectPersonRepository.save(projectPersonEntity);
    }

    @Validated(RemoveValidationGroup.class)
    @Transactional
    public void removeProjectPerson(
            @Valid @NotNull (groups= {RemoveValidationGroup.class}) Long projectId,
            @Valid @NotNull (groups= {RemoveValidationGroup.class}) Long personId) {
        projectPersonRepository.delete(ProjectPersonEntity.builder().projectId(projectId).personId(personId).build());
    }

    @Validated(RemoveValidationGroup.class)
    @Transactional
    public void removeProjectPersonList(
            @Valid @NotEmpty (groups= {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) ProjectPersonEntityId> projectPersonEntityIdList) {
        List<ProjectPersonEntity> projectPersonList = new ArrayList<>();
        for(int i=0;i < projectPersonEntityIdList.size() ;i++) {
            ProjectPersonEntityId projectPersonEntityId = projectPersonEntityIdList.get(i);
            Long personId = projectPersonEntityId.getPersonId();
            Long projectId = projectPersonEntityId.getProjectId();
            projectPersonList.add(ProjectPersonEntity.builder().projectId(projectId).personId(personId).build());
        }
        projectPersonRepository.deleteAll(projectPersonList);
    }





}
