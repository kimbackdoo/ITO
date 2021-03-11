package kr.co.metasoft.ito.common.entity.jpa.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class RoleMenuId implements Serializable {

    private static final long serialVersionUID = 3558704759815895091L;

    @Column (name = "role_id")
    private String roleId;

    @Column (name = "menu_id")
    private String menuId;

}