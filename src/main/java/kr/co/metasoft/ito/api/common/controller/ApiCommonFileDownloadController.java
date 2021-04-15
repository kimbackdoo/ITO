package kr.co.metasoft.ito.api.common.controller;

import kr.co.metasoft.ito.api.common.dto.PersonParamDto;
import kr.co.metasoft.ito.api.common.service.DataDownloadService;
import kr.co.metasoft.ito.common.util.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/common/downloads")
public class ApiCommonFileDownloadController {
    @Autowired
    private DataDownloadService dataDownloadService;

    @GetMapping(path = "list.xlsx", produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"})
    public byte[] getPersonListXlsx(
            @ModelAttribute PersonParamDto personParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return dataDownloadService.getPersonListXlsx(personParamDto, pageRequest);
    }
}
