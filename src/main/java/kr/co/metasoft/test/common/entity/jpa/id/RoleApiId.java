package kr.co.metasoft.test.common.entity.jpa.id;

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
public class RoleApiId implements Serializable {

    private static final long serialVersionUID = 8067245582918636650L;

    @Column (name = "role_id")
    private String roleId;

    private ApiId apiId;

}