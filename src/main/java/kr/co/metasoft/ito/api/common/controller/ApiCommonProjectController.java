package kr.co.metasoft.ito.api.common.controller;

import kr.co.metasoft.ito.api.common.dto.ProjectParamDto;
import kr.co.metasoft.ito.api.common.entity.ProjectEntity;
import kr.co.metasoft.ito.api.common.service.ProjectService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/common/projects")
public class ApiCommonProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping(path = "")
    public PageResponse<ProjectEntity> getProjectList(
            @ModelAttribute ProjectParamDto projectParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return projectService.getProjectList(projectParamDto, pageRequest);
    }

    @GetMapping(path = "{adminProjectId}")
    public ProjectEntity getProject(
            @PathVariable(name = "adminProjectId") Long adminProjectId) {
        return projectService.getProfile(adminProjectId);
    }

    @PostMapping(path = "")
    public ProjectEntity createProject(
            @RequestBody ProjectEntity projectEntity) {
        return projectService.createProject(projectEntity);
    }

    @PutMapping(path = "{adminProjectId}")
    public ProjectEntity modifyProject(
            @PathVariable(name = "adminProjectId") Long adminProjectId,
            @RequestBody ProjectEntity projectEntity) {
        projectEntity.setAdminProjId(adminProjectId);
        return projectService.modifyProject(projectEntity);
    }

    @DeleteMapping(path = "")
    public void removeProjectList(
            @RequestBody List<Long> idList) {
        projectService.removeProjectList(idList);
    }
}
