package kr.co.metasoft.ito.api.common.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DataUploadParamDto {

    private MultipartFile file;

    private Integer fileType;

    private String uploadReferenceMonth;

}


