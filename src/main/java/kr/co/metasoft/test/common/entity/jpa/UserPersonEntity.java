package kr.co.metasoft.test.common.entity.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table (schema = "test", name = "tb_user_person")
@Getter
@Setter
public class UserPersonEntity {

    @Id
    @Column (name = "user_id")
    private String userId;

    @Column (name = "person_id")
    private Long personId;

}