package kr.co.metasoft.ito.api.common.controller;

import java.util.ArrayList;
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

import kr.co.metasoft.ito.api.common.dto.ProjectSkillDto;
import kr.co.metasoft.ito.api.common.dto.ProjectSkillParamDto;
import kr.co.metasoft.ito.api.common.entity.ProjectSkillEntity;
import kr.co.metasoft.ito.api.common.entity.id.ProjectSkillEntityId;
import kr.co.metasoft.ito.api.common.service.ProjectSkillService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@RestController
@RequestMapping (path = "api/common/project-skills")
public class ApiCommonProjectSkillController {

    @Autowired
    private ProjectSkillService projectSkillService;

    @GetMapping (path = "")
    public PageResponse<ProjectSkillEntity> getProjectSkillList(
            @ModelAttribute ProjectSkillParamDto projectSkillParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return projectSkillService.getProjectSkillList(projectSkillParamDto, pageRequest);
    }

    @GetMapping (path = "{projectId},{skill}")
    public ProjectSkillEntity getProjectSkill(
            @PathVariable (name = "projectId") Long projectId,
            @PathVariable (name = "skill") String skill) {
        return projectSkillService.getProjectSkill(projectId, skill);
    }

    @PostMapping (path = "", params = {"bulk"})
    public List<ProjectSkillEntity> createProjectSkillList(
            @RequestBody List<ProjectSkillDto> projectSkillDtoList) {
        List<ProjectSkillEntity> projectSkillList = new ArrayList<>();
        for (int i = 0; i < projectSkillDtoList.size(); i++) {
            ProjectSkillDto projectSkillDto = projectSkillDtoList.get(i);
            ProjectSkillEntity projectSkillEntity = ProjectSkillEntity.builder()
                    .projId(projectSkillDto.getProjId())
                    .skill(projectSkillDto.getSkill())
                    .build();
            projectSkillList.add(projectSkillEntity);
        }
        return projectSkillService.createProjectSkillList(projectSkillList);
    }

    @PostMapping (path = "", params = {"!bulk"})
    public ProjectSkillEntity createProjectSkill(
            @RequestBody ProjectSkillDto projectSkillDto) {
        ProjectSkillEntity projectSkillEntity = ProjectSkillEntity.builder()
                .projId(projectSkillDto.getProjId())
                .skill(projectSkillDto.getSkill())
                .build();
        return projectSkillService.createProjectSkill(projectSkillEntity);
    }

    @PutMapping (path = "")
    public List<ProjectSkillEntity> modifyProjectSkillList(
            @RequestBody List<ProjectSkillDto> projectSkillDtoList) {
        List<ProjectSkillEntity> projectSkillList = new ArrayList<>();
        for (int i = 0; i < projectSkillDtoList.size(); i++) {
            ProjectSkillDto projectSkillDto = projectSkillDtoList.get(i);
            ProjectSkillEntity projectSkillEntity = ProjectSkillEntity.builder()
                    .projId(projectSkillDto.getProjId())
                    .skill(projectSkillDto.getSkill())
                    .build();
            projectSkillList.add(projectSkillEntity);
        }
        return projectSkillService.modifyProjectSkillList(projectSkillList);
    }

    @PutMapping (path = "{projectId},{skill}")
    public ProjectSkillEntity modifyProjectSkill(
            @PathVariable (name = "projectId") Long projectId,
            @PathVariable (name = "skill") String skill,
            @RequestBody ProjectSkillDto projectSkillDto) {
        ProjectSkillEntity projectSkillEntity = ProjectSkillEntity.builder()
                .projId(projectId)
                .skill(skill)
                .build();
        return projectSkillService.modifyProjectSkill(projectSkillEntity);
    }

    @DeleteMapping (path = "")
    public void removeProjectSkillList(
            @RequestBody List<ProjectSkillEntityId> projectSkillEntityIdList) {
        projectSkillService.removeProjectSkillList(projectSkillEntityIdList);
    }

    @DeleteMapping (path = "{projectId},{skill}")
    public void removeProjectSkill(
            @PathVariable (name = "projectId") Long projectId,
            @PathVariable (name = "skill") String skill) {
        projectSkillService.removeProjectSkill(projectId, skill);
    }

}