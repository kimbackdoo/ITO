package kr.co.metasoft.ito.api.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContactParamDto {

    private String condition;

    private String keyword;
}