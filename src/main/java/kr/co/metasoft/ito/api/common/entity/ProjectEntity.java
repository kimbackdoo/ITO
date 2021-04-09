package kr.co.metasoft.ito.api.common.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_proj")
@EntityListeners (value = {AuditingEntityListener.class})
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
    @Column(name = "id" , columnDefinition = "bigint(20)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "`local`", columnDefinition = "varchar(100)", nullable = true)
    private String local;

    @Column(name = "detail_local", columnDefinition = "varchar(100)", nullable = true)
    private String detailLocal;

    @Column (name = "`postcode`", columnDefinition = "bigint(20)", nullable = true)
    private String postcode;

    @Column (name = "`address`", columnDefinition = "varchar(100)", nullable = true)
    private String address;

    @Column (name = "`detail_address`", columnDefinition = "varchar(100)", nullable = true)
    private String detailAddress;

    @Column(name = "prsnl", columnDefinition = "int(11)", nullable = true)
    private Integer prsnl;

    @Column (name = "`memo`", columnDefinition = "text", nullable = true)
    private String memo;

    @Column(name = "status", columnDefinition = "varchar(100)", nullable = true)
    private Character status;

    @Column(name = "salary", columnDefinition = "bigint(20)", nullable = true)
    private Long salary;

    @Column(name = "term" , columnDefinition = "varchar(255)", nullable = true)
    private String term;

    @JsonFormat (pattern = "yyyy-MM-dd")
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    @Column (name = "`limit_date`", columnDefinition = "date", nullable = true)
    private LocalDate limitDate;

    @CreatedDate
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @Column (name = "`created_date`", columnDefinition = "datetime", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @Column (name = "`last_modified_date`", columnDefinition = "datetime", nullable = false)
    private LocalDateTime lastModifiedDate;

}
