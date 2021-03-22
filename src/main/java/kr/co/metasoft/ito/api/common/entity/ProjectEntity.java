package kr.co.metasoft.ito.api.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_proj")
@Getter
@Setter
public class ProjectEntity {
    @Id
    @Column(name = "admin_proj_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminProjId;

    @Column(name = "name")
    private String name;

    @Column(name = "job")
    private String job;

    @Column(name = "skill")
    private String skill;

    @Column(name = "career")
    private String career;

    @Column(name = "degree")
    private String degree;

    @Column(name = "term")
    private String term;

    @Column(name = "place")
    private String place;

    @Column(name = "prsnl")
    private int prsnl;

    @Column(name = "status")
    private String status;

    @Column(name = "salary")
    private Long salary;
}
