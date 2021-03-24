package kr.co.metasoft.ito.api.common.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.metasoft.ito.api.common.dto.DataUploadParamDto;
import kr.co.metasoft.ito.api.common.service.DataUploadService;


@RestController
@RequestMapping(path = "api/app/settings/excel-file-upload")
class ApiCommonFileUploadController {

    @Autowired
    DataUploadService dataUploadService;

    // excel로 업로드만 가능하게끔
    @PostMapping (path = "")
    public Map<String, Object> getExcelFileUpload(
            DataUploadParamDto dataUploadParamDto) throws Exception {
            return dataUploadService.uploadCMDBExcelFile(dataUploadParamDto);
    }

    /*
DataUploadParamDto  => sevice로 param 을 넘기는데... 파일 타입?

    private MultipartFile file;

    private Integer fileType;

    private String uploadReferenceMonth;


    */

    /*
     * @GetMapping (path = "/app/settings/upload-file-log") public
     * PageResponse<UploadFileLogDto> getUploadFileLogByFileType(
     * UploadFileLogParamDto uploadFileLogParamDto, PageRequest pageRequest) throws
     * IOException { if(uploadFileLogParamDto.getUploadType().equals("CMDB")) return
     * dataUploadService.selectCMDBUploadFileLog(pageRequest); else return null; }
     */

}
