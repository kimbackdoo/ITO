package kr.co.metasoft.ito.api.common.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import kr.co.metasoft.ito.api.common.dto.ProjectPersonParamDto;
import kr.co.metasoft.ito.api.common.entity.ProjectPersonEntity;
import kr.co.metasoft.ito.api.common.mapper.ProjectPersonMapper;
import kr.co.metasoft.ito.api.common.repository.ProjectPersonRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;

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
        return pageResponse;
    }

}
