package kr.co.metasoft.ito.api.common.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "`tb_approval`")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalEntity {

    @NotNull(groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Id
    @Column (name = "`id`", columnDefinition = "bigint(20)")
    private Long id;

    @NotNull(groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Column (name = "`vacation_id`", columnDefinition = "bigint(20)")
    private Long vacationId;

    @Column (name = "`step`", columnDefinition = "bigint(20)", nullable = true)
    private Long step;

    @Column (name = "`team_leader`", columnDefinition = "enum('T','F')", nullable = true)
    private String teamLeader;

    @Column (name = "`director`", columnDefinition = "enum('T','F')", nullable = true)
    private String director;

    @Column (name = "`president`", columnDefinition = "enum('T','F')", nullable = true)
    private String president;

    @JsonFormat (pattern = "yyyy-MM-dd")
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    @Column (name = "`approval_date`", columnDefinition = "date", nullable = true)
    private LocalDate approvalDate;

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
