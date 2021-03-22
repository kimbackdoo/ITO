package kr.co.metasoft.ito.api.common.service;

import kr.co.metasoft.ito.api.common.dto.ProjectDto;
import kr.co.metasoft.ito.api.common.entity.ProjectEntity;
import kr.co.metasoft.ito.api.common.mapper.ProjectMapper;
import kr.co.metasoft.ito.api.common.repository.ProjectRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import org.apache.tools.ant.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @Transactional
    public PageResponse<ProjectEntity> getProjectList(
            @Valid ProjectDto projectDto,
            PageRequest pageRequest) {
        Integer projectListCount = projectMapper.selectProjectListCount(projectDto);
        List<ProjectEntity> projectList = projectMapper.selectProjectList(projectDto, pageRequest);
        PageResponse<ProjectEntity> pageResponse = new PageResponse<>(pageRequest, projectListCount);
        pageResponse.setItems(projectList);
        return pageResponse;
    }

    @Transactional
    public ProjectEntity getProfile(Long adminProjId) { return projectRepository.findById(adminProjId).orElse(null); }

    @Transactional
    public ProjectEntity createProject(ProjectEntity projectEntity) { return projectRepository.save(projectEntity); }

    @Transactional
    public ProjectEntity modifyProject(ProjectEntity projectEntity) { return projectRepository.save(projectEntity); }

    @Transactional
    public void removeProjectList(List<Long> idList) { }
}
