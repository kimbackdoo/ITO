package kr.co.metasoft.ito.api.common.controller;


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

import kr.co.metasoft.ito.api.common.dto.ProjectCareerDto;
import kr.co.metasoft.ito.api.common.dto.ProjectCareerParamDto;
import kr.co.metasoft.ito.api.common.entity.ProjectCareerEntity;
import kr.co.metasoft.ito.api.common.entity.id.ProjectCareerEntityId;
import kr.co.metasoft.ito.api.common.service.ProjectCareerService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@RestController
@RequestMapping(path ="/api/common/project-careers")
public class ApiCommonProjectCareerController {

    @Autowired
    private ProjectCareerService projectCareerService;

    @GetMapping (path = "")
    public PageResponse<ProjectCareerEntity> getProjectCareerList(
            @ModelAttribute ProjectCareerParamDto projectCareerParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return projectCareerService.getprojectCareerList(projectCareerParamDto, pageRequest);
    }

    @PostMapping(path = "")
    public ProjectCareerEntity careateProjectCareer(
            @RequestBody ProjectCareerDto projectCareerDto) {
            ProjectCareerEntity projectCareerEntity = ProjectCareerEntity.builder()
                    .projectId(projectCareerDto.getProjectId())
                    .careerId(projectCareerDto.getCareerId())
                    .build();
        return projectCareerService.createProjectCareer(projectCareerEntity);
    }

    @PutMapping(path = "{careerId},{projectId}")
    public ProjectCareerEntity modifyProjectCareer(
            @PathVariable (name = "careerId") Long careerId,
            @PathVariable (name = "projectId") Long projectId,
            @RequestBody ProjectCareerDto projectCareerDto) {
            ProjectCareerEntity projectCareerEntity = ProjectCareerEntity.builder()
                    .projectId(projectCareerDto.getProjectId())
                    .careerId(projectCareerDto.getCareerId())
                    .build();
        return projectCareerService.modifyProjectCareer(projectCareerEntity);
    }



    @DeleteMapping (path = "{projectId},{careerId}")
    public void removeProjectCareer(
            @PathVariable (name = "projectId") Long projectId,
            @PathVariable (name = "careerId") Long careerId) {
        projectCareerService.removeProjectCareer(projectId, careerId);
    }

    @DeleteMapping (path = "")
    public void removeProjectCareerList(
            @RequestBody List<ProjectCareerEntityId> projectCareerEntityIdList) {
        projectCareerService.removeProjectCareerList(projectCareerEntityIdList);
    }



}