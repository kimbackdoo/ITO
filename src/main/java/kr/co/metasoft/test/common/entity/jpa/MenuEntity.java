package kr.co.metasoft.test.common.entity.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table (schema = "test", name = "tb_menu")
@Getter
@Setter
public class MenuEntity {

    @Id
    @Column (name = "id")
    private String id;

    @Column (name = "parent_id")
    private String parentId;

    @Column (name = "name")
    private String name;

    @Column (name = "description")
    private String description;

    @Column (name = "url")
    private String url;

    @Column (name = "icon")
    private String icon;

    @Column (name = "ranking")
    private Short ranking;

}