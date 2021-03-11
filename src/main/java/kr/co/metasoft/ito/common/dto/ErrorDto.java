package kr.co.metasoft.ito.common.dto;

import java.util.List;

import org.springframework.validation.FieldError;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto {

    private String timestamp;

    private Integer status;

    private String error;

    private String message;

    private String code;

    private String path;

    private List<FieldError> fieldErrors;

}