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

import kr.co.metasoft.ito.api.common.dto.ProjectParamDto;
import kr.co.metasoft.ito.api.common.entity.ProjectEntity;
import kr.co.metasoft.ito.api.common.mapper.ProjectMapper;
import kr.co.metasoft.ito.api.common.repository.ProjectRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @Transactional
    public PageResponse<ProjectEntity> getProjectList(
            @Valid ProjectParamDto projectParamDto,
            PageRequest pageRequest) {
        Integer projectListCount = projectMapper.selectProjectListCount(projectParamDto);
        List<ProjectEntity> projectList = projectMapper.selectProjectList(projectParamDto, pageRequest);
        PageResponse<ProjectEntity> pageResponse = new PageResponse<>(pageRequest, projectListCount);
        pageResponse.setItems(projectList);

        System.out.println("===========================================================================");
        System.out.println("NameLike: " + projectParamDto.getNameLike());
        System.out.println("Job: " + projectParamDto.getJob());
        System.out.println("skillList: " + projectParamDto.getSkillList());
        System.out.println("stermStart: " + projectParamDto.getStermStart());
        System.out.println("prsnl: " + projectParamDto.getPrsnl());
        System.out.println("status: " + projectParamDto.getStatus());
        System.out.println("salary: " + projectParamDto.getSalary());
        System.out.println("===========================================================================");

        return pageResponse;
    }

    @Transactional
    public ProjectEntity getProfile(Long id) { return projectRepository.findById(id).orElse(null); }

    @Transactional
    public ProjectEntity createProject(ProjectEntity projectEntity) { return projectRepository.save(projectEntity); }

    @Transactional
    public ProjectEntity modifyProject(ProjectEntity projectEntity) { return projectRepository.save(projectEntity); }

    /*
     * @Transactional public void removeProjectList(List<Long> idList) { }
     */

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removeProjectList(
            @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) Long> idList) {
            List<ProjectEntity> projectList = new ArrayList<>();
            for (int i = 0; i < idList.size(); i++) {
                Long id = idList.get(i);
                projectList.add(ProjectEntity.builder().id(id).build());
             }
        projectRepository.deleteAll(projectList);
    }





}
