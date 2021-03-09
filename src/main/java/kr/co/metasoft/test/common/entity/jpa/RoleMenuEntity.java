package kr.co.metasoft.test.common.entity.jpa;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import kr.co.metasoft.test.common.entity.jpa.id.RoleMenuId;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (schema = "test", name = "tb_role_menu")
@Getter
@Setter
public class RoleMenuEntity {

    @EmbeddedId
    private RoleMenuId roleMenuId;

}