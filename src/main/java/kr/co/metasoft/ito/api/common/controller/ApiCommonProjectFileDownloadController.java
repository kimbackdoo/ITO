package kr.co.metasoft.ito.api.common.controller;

import kr.co.metasoft.ito.api.common.dto.ProjectParamDto;
import kr.co.metasoft.ito.api.common.service.ProjectDataDownloadService;
import kr.co.metasoft.ito.common.util.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "api/app/project-downloads")
public class ApiCommonProjectFileDownloadController {
    @Autowired
    private ProjectDataDownloadService projectDataDownloadService;

    @GetMapping(path = "list.xlsx", produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"})
    public byte[] getPersonListXlsx(
            @ModelAttribute ProjectParamDto projectParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return projectDataDownloadService.getProjectListXlsx(projectParamDto, pageRequest);
    }
}
