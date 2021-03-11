package kr.co.metasoft.ito.api.app.menu.dto;

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