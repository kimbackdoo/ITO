package kr.co.metasoft.test.api.app.api.dto;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class ApiDto {

    private String url;

    private String method;

    private String name;

    private String description;

}