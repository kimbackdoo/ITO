package kr.co.metasoft.ito.api.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_proj")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity {

    @Null (groups = {CreateValidationGroup.class})
    @NotNull (groups = {ModifyValidationGroup.class})
    @Id
    @Column(name = "admin_proj_id" , columnDefinition = "bigint(20)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminProjId;

    @Column(name = "name" , columnDefinition = "varchar(100)", nullable = true)
    private String name;

    @Column(name = "job" , columnDefinition = "varchar(100)", nullable = true)
    private String job;

    @Column(name = "skill" , columnDefinition = "varchar(100)", nullable = true)
    private String skill;

    @Column(name = "career" , columnDefinition = "varchar(100)", nullable = true)
    private String career;

    @Column(name = "degree", columnDefinition = "varchar(100)", nullable = true)
    private String degree;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column (name = "`sterm`", columnDefinition = "date", nullable = true)
    private LocalDate sterm;

    @JsonFormat (pattern = "yyyy-MM-dd")
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    @Column (name = "`eterm`", columnDefinition = "date", nullable = true)
    private LocalDate eterm;

    @Column(name = "place", columnDefinition = "varchar(100)", nullable = true)
    private String place;

    @Column(name = "prsnl", columnDefinition = "int(11)", nullable = true)
    private Integer prsnl;

    @Column(name = "status", columnDefinition = "varchar(100)", nullable = true)
    private String status;

    @Column(name = "salary", columnDefinition = "bigint(20)", nullable = true)
    private Long salary;

    @Column(name = "term" , columnDefinition = "varchar(255)", nullable = true)
    private String term;


}
