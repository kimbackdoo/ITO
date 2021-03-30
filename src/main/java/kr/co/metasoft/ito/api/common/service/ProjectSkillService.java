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

import kr.co.metasoft.ito.api.common.dto.ProjectSkillParamDto;
import kr.co.metasoft.ito.api.common.entity.ProjectSkillEntity;
import kr.co.metasoft.ito.api.common.entity.id.ProjectSkillEntityId;
import kr.co.metasoft.ito.api.common.mapper.ProjectSkillMapper;
import kr.co.metasoft.ito.api.common.repository.ProjectSkillRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

@Validated
@Service
public class ProjectSkillService {

    @Autowired
    private ProjectSkillRepository projectSkillRepository;

    @Autowired
    private ProjectSkillMapper projectSkillMapper;

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public PageResponse<ProjectSkillEntity> getProjectSkillList(
            @Valid ProjectSkillParamDto projectSkillParamDto,
            PageRequest pageRequest) {
        Integer projectSkillListCount = projectSkillMapper.selectProjectSkillListCount(projectSkillParamDto);
        List<ProjectSkillEntity> projectSkillList = projectSkillMapper.selectProjectSkillList(projectSkillParamDto, pageRequest);
        PageResponse<ProjectSkillEntity> pageResponse = new PageResponse<>(pageRequest, projectSkillListCount);
        pageResponse.setItems(projectSkillList);
        return pageResponse;
    }

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public ProjectSkillEntity getProjectSkill(
            @Valid @NotNull (groups = {ReadValidationGroup.class}) Long projectId,
            @Valid @NotNull (groups = {ReadValidationGroup.class}) String skill) {
        return projectSkillMapper.selectProjectSkill(projectId, skill);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public List<ProjectSkillEntity> createProjectSkillList(
            @Valid @NotEmpty (groups = {CreateValidationGroup.class}) List<@NotNull (groups = {CreateValidationGroup.class}) ProjectSkillEntity> projectSkillList) {
        return projectSkillRepository.saveAll(projectSkillList);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public ProjectSkillEntity createProjectSkill(
            @Valid @NotNull (groups = {CreateValidationGroup.class}) ProjectSkillEntity projectSkillEntity) {
        return projectSkillRepository.save(projectSkillEntity);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public List<ProjectSkillEntity> modifyProjectSkillList(
            @Valid @NotEmpty (groups = {ModifyValidationGroup.class}) List<@NotNull (groups = {ModifyValidationGroup.class}) ProjectSkillEntity> projectSkillList) {
        return projectSkillRepository.saveAll(projectSkillList);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public ProjectSkillEntity modifyProjectSkill(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) ProjectSkillEntity projectSkillEntity) {
        return projectSkillRepository.save(projectSkillEntity);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removeProjectSkillList(
            @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) ProjectSkillEntityId> projectSkillEntityIdList) {
        List<ProjectSkillEntity> projectSkillList = new ArrayList<>();
        for (int i = 0; i < projectSkillEntityIdList.size(); i++) {
            ProjectSkillEntityId projectSkillEntityId = projectSkillEntityIdList.get(i);
            Long projectId = projectSkillEntityId.getProjId();
            String skill = projectSkillEntityId.getSkill();
            projectSkillList.add(ProjectSkillEntity.builder().projId(projectId).skill(skill).build());
        }
        projectSkillRepository.deleteAll(projectSkillList);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removeProjectSkill(
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) Long projectId,
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) String skill) {
        projectSkillRepository.delete(ProjectSkillEntity.builder().projId(projectId).skill(skill).build());
    }

}