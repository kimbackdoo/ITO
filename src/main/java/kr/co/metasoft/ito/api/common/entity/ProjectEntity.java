package kr.co.metasoft.ito.api.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

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

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column (name = "`sterm`", columnDefinition = "date", nullable = true)
    private LocalDate sterm;

    @JsonFormat (pattern = "yyyy-MM-dd")
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    @Column (name = "`eterm`", columnDefinition = "date", nullable = true)
    private LocalDate eterm;

    @Column(name = "place")
    private String place;

    @Column(name = "prsnl")
    private Integer prsnl;

    @Column(name = "status")
    private String status;

    @Column(name = "salary")
    private Long salary;

    @Column(name = "term")
    private String term;
}
