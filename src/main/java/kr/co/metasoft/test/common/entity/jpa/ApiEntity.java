package kr.co.metasoft.test.common.entity.jpa;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import kr.co.metasoft.test.common.entity.jpa.id.ApiId;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (schema = "test", name = "tb_api")
@Getter
@Setter
public class ApiEntity {

    @EmbeddedId
    private ApiId apiId;

    @Column (name = "name")
    private String name;

    @Column (name = "description")
    private String description;

}