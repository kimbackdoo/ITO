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

import kr.co.metasoft.ito.api.common.dto.ProjectPersonDto;
import kr.co.metasoft.ito.api.common.dto.ProjectPersonParamDto;
import kr.co.metasoft.ito.api.common.dto.UserPersonParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.entity.ProjectPersonEntity;
import kr.co.metasoft.ito.api.common.entity.UserPersonEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonLanguageEntityId;
import kr.co.metasoft.ito.api.common.entity.id.ProjectPersonEntityId;
import kr.co.metasoft.ito.api.common.service.ProjectPersonService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@RestController
@RequestMapping(path ="/api/common/project-person")
public class ApiCommonProjectPersonController {

    @Autowired
    private ProjectPersonService projectPersonService;

    @GetMapping (path = "")
    public PageResponse<ProjectPersonEntity> getProjectPersonList(
            @ModelAttribute ProjectPersonParamDto projectPersonParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return projectPersonService.getprojectPersonList(projectPersonParamDto, pageRequest);
    }

    @PostMapping(path = "")
    public ProjectPersonEntity careateProjectPerson(
            @RequestBody ProjectPersonDto projectPersonDto) {
            ProjectPersonEntity projectPersonEntity = ProjectPersonEntity.builder()
                    .projectId(projectPersonDto.getProjectId())
                    .personId(projectPersonDto.getPersonId())
                    .build();
        return projectPersonService.createProjectPerson(projectPersonEntity);
    }

    @PutMapping(path = "{personId},{projectId}")
    public ProjectPersonEntity modifyProjectPerson(
            @PathVariable (name = "personId") Long personId,
            @PathVariable (name = "projectId") Long projectId,
            @RequestBody ProjectPersonDto projectPersonDto) {
            ProjectPersonEntity projectPersonEntity = ProjectPersonEntity.builder()
                    .projectId(projectPersonDto.getProjectId())
                    .personId(projectPersonDto.getPersonId())
                    .status(projectPersonDto.getStatus())
                    .build();
        return projectPersonService.modifyProjectPerson(projectPersonEntity);
    }



    @DeleteMapping (path = "{projectId},{personId}")
    public void removeProjectPerson(
            @PathVariable (name = "projectId") Long projectId,
            @PathVariable (name = "personId") Long personId) {
        projectPersonService.removeProjectPerson(projectId, personId);
    }

    @DeleteMapping (path = "")
    public void removeProjectPersonList(
            @RequestBody List<ProjectPersonEntityId> projectPersonEntityIdList) {
        projectPersonService.removeProjectPersonList(projectPersonEntityIdList);
    }



}