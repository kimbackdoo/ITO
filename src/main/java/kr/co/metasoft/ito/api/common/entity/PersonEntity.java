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
@Table (name = "`tb_person`")
@EntityListeners (value = {AuditingEntityListener.class})
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity {

    @Null (groups = {CreateValidationGroup.class})
    @NotNull (groups = {ModifyValidationGroup.class})
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "`id`", columnDefinition = "bigint(20)")
    private Long id;

    @Column (name = "`name`", columnDefinition = "varchar(100)", nullable = true)
    private String name;

    @Column (name = "`phone_number`", columnDefinition = "varchar(13)", nullable = true)
    private String phoneNumber;

    @Column (name = "`job_type`", columnDefinition = "varchar(100)", nullable = true)
    private String jobType;

    @Column (name = "`input_status`", columnDefinition = "char(1)", nullable = true)
    private String inputStatus;

    @Column (name = "`certificate_status`", columnDefinition = "char(1)", nullable = true)
    private String certificateStatus;

    @Column (name = "`role`", columnDefinition = "varchar(100)", nullable = true)
    private String role;

    @Column (name = "`career`", columnDefinition = "varchar(100)", nullable = true)
    private String career;

    @Column (name = "`days`", columnDefinition = "varchar(100)", nullable = true)
    private String days;

    @Column (name = "`min_pay`", columnDefinition = "bigint(20)", nullable = true)
    private Long minPay;

    @Column (name = "`max_pay`", columnDefinition = "bigint(20)", nullable = true)
    private Long maxPay;

    @JsonFormat (pattern = "yyyy-MM-dd")
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    @Column (name = "`workable_day`", columnDefinition = "date", nullable = true)
    private LocalDate workableDay;

    @Column (name = "`postcode`", columnDefinition = "bigint(20)", nullable = true)
    private String postcode;

    @Column (name = "`address`", columnDefinition = "varchar(100)", nullable = true)
    private String address;

    @Column (name = "`detail_address`", columnDefinition = "varchar(100)", nullable = true)
    private String detailAddress;

    @JsonFormat (pattern = "yyyy-MM-dd")
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    @Column (name = "`birth_date`", columnDefinition = "date", nullable = true)
    private LocalDate birthDate;

    @Column (name = "`email`", columnDefinition = "varchar(100)", nullable = true)
    private String email;

    @Column (name = "`website`", columnDefinition = "varchar(100)", nullable = true)
    private String website;

    @Column (name = "`education`", columnDefinition = "varchar(100)", nullable = true)
    private String education;

    @Column (name = "`status`", columnDefinition = "enum('T', 'F')", nullable = true)
    private String status;

    @Column (name = "`memo`", columnDefinition = "text", nullable = true)
    private String memo;

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