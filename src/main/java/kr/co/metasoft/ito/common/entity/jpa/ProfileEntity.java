package kr.co.metasoft.ito.common.entity.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "ITO", name = "tb_profile")
@Getter
@Setter
public class ProfileEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "job")
    private String job;

    @Column(name = "skill")
    private String skill;

    @Column(name = "birth")
    private String birth;

    @Column(name = "career")
    private String career;

    @Column(name = "email")
    private String email;
}
