package kr.co.metasoft.test.api.app.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDto {

    private String id;

    private String parentId;

    private String name;

    private String url;

    private String icon;

    private Short ranking;

    private String rankingPath;

    private Short depth;

}