package kr.co.metasoft.ito.api.common.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.common.dto.ProjectPersonParamDto;
import kr.co.metasoft.ito.api.common.dto.UserPersonParamDto;
import kr.co.metasoft.ito.api.common.entity.ProjectPersonEntity;
import kr.co.metasoft.ito.api.common.entity.UserPersonEntity;
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

}