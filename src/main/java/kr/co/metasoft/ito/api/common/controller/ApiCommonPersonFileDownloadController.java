package kr.co.metasoft.ito.api.common.controller;

import kr.co.metasoft.ito.api.common.dto.PersonParamDto;
import kr.co.metasoft.ito.api.common.dto.ProjectPersonParamDto;
import kr.co.metasoft.ito.api.common.service.PersonDataDownloadService;
import kr.co.metasoft.ito.common.util.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/app/person-downloads")
public class ApiCommonPersonFileDownloadController {
    @Autowired
    private PersonDataDownloadService personDataDownloadService;

    @GetMapping(path = "list.xlsx", produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"})
    public byte[] getPersonListXlsx(
            @ModelAttribute PersonParamDto personParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return personDataDownloadService.getPersonListXlsx(personParamDto, pageRequest);
    }


}
