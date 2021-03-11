package kr.co.metasoft.ito.common.entity.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table (schema = "ITO", name = "tb_role")
@Getter
@Setter
public class RoleEntity {

    @Id
    @Column (name = "id")
    private String id;

    @Column (name = "name")
    private String name;

    @Column (name = "description")
    private String description;

}