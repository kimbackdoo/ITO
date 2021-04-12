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

import kr.co.metasoft.ito.api.common.dto.ProjectCareerParamDto;
import kr.co.metasoft.ito.api.common.entity.ProjectCareerEntity;
import kr.co.metasoft.ito.api.common.entity.id.ProjectCareerEntityId;
import kr.co.metasoft.ito.api.common.mapper.ProjectCareerMapper;
import kr.co.metasoft.ito.api.common.repository.ProjectCareerRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;



@Validated
@Service
public class ProjectCareerService {


    @Autowired
    private ProjectCareerRepository projectCareerRepository;

    @Autowired
    private ProjectCareerMapper projectCareerMapper;

    @Validated(ReadValidationGroup.class)
    @Transactional (readOnly = true)
    public PageResponse<ProjectCareerEntity> getprojectCareerList(
            @Valid ProjectCareerParamDto projectCareerParamDto,
            PageRequest pageRequest){
        Integer projectCareerListCount = projectCareerMapper.selectProjectCareerListCount(projectCareerParamDto);
        List<ProjectCareerEntity> projectCareerList = projectCareerMapper.selectProjectCareerList(projectCareerParamDto, pageRequest);
        PageResponse<ProjectCareerEntity> pageResponse = new PageResponse<>(pageRequest, projectCareerListCount);
        pageResponse.setItems(projectCareerList);
        return pageResponse;
    }

    @Validated(ReadValidationGroup.class)
    @Transactional (readOnly = true)
    public List<Long> selectCareerIdList(
            @Valid ProjectCareerParamDto projectCareerParamDto){
        List<ProjectCareerEntity> projectCareerList = projectCareerMapper.selectCareerIdList(projectCareerParamDto);
        List<Long> careerIdList = new ArrayList<>();
        for(int i=0;i < projectCareerList.size() ;i++) {
            careerIdList.add(projectCareerList.get(i).getCareerId());
        }
        return careerIdList;
    }


    @Validated(CreateValidationGroup.class)
    @Transactional
    public ProjectCareerEntity createProjectCareer(
            @Valid @NotNull (groups= {CreateValidationGroup.class}) ProjectCareerEntity projectCareerEntity) {
        return projectCareerRepository.save(projectCareerEntity);
    }


    @Validated(CreateValidationGroup.class)
    @Transactional
    public ProjectCareerEntity modifyProjectCareer(
            @Valid @NotNull (groups= {CreateValidationGroup.class}) ProjectCareerEntity projectCareerEntity) {
        return projectCareerRepository.save(projectCareerEntity);
    }

    @Validated(RemoveValidationGroup.class)
    @Transactional
    public void removeProjectCareer(
            @Valid @NotNull (groups= {RemoveValidationGroup.class}) Long projectId,
            @Valid @NotNull (groups= {RemoveValidationGroup.class}) Long careerId) {
        projectCareerRepository.delete(ProjectCareerEntity.builder().projectId(projectId).careerId(careerId).build());
    }

    @Validated(RemoveValidationGroup.class)
    @Transactional
    public void removeProjectCareerList(
            @Valid @NotEmpty (groups= {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) ProjectCareerEntityId> projectCareerEntityIdList) {
        List<ProjectCareerEntity> projectCareerList = new ArrayList<>();
        for(int i=0;i < projectCareerEntityIdList.size() ;i++) {
            ProjectCareerEntityId projectCareerEntityId = projectCareerEntityIdList.get(i);
            Long careerId = projectCareerEntityId.getCareerId();
            Long projectId = projectCareerEntityId.getProjectId();
            projectCareerList.add(ProjectCareerEntity.builder().projectId(projectId).careerId(careerId).build());
        }
        projectCareerRepository.deleteAll(projectCareerList);
    }





}
