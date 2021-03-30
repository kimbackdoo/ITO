package kr.co.metasoft.ito.api.common.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.common.dto.DataUploadParamDto;
import kr.co.metasoft.ito.api.common.service.DataUploadService;


@RestController
@RequestMapping(path = "api/app/uploads")
class ApiCommonFileUploadController {

    @Autowired
    DataUploadService dataUploadService;

    // excel로 업로드만 가능하게끔
    @PostMapping (path = "/person")
    public Map<String, Object> getPersonExcelFile(
            DataUploadParamDto dataUploadParamDto) throws Exception {
            return dataUploadService.uploadCMDBExcelFile(dataUploadParamDto);
    }
}
