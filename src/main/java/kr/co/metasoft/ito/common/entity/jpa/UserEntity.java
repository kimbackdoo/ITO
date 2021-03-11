package kr.co.metasoft.ito.common.entity.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table (schema = "ITO", name = "tb_user")
@Getter
@Setter
public class UserEntity {

    @Id
    @Column (name = "id")
    private String id;

    @Column (name = "password")
    private String password;

}