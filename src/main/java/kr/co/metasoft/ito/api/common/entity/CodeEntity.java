package kr.co.metasoft.ito.api.common.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@Table (
        name = "`tb_code`",
        uniqueConstraints = {
                @UniqueConstraint (columnNames = {"`parent_id`", "`name`"})
        }
)
@EntityListeners (value = {AuditingEntityListener.class})
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeEntity {

    @Null (groups = {CreateValidationGroup.class})
    @NotNull (groups = {ModifyValidationGroup.class})
    @Id
    @Column (name = "`id`", columnDefinition = "varchar(15)")
    private String id;

    @Column (name = "`parent_id`", columnDefinition = "varchar(13)", nullable = true)
    private String parentId;

    @NotNull (groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Column (name = "`name`", columnDefinition = "varchar(100)", nullable = false)
    private String name;

    @NotNull (groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Column (name = "`ranking`", columnDefinition = "int(11)", nullable = false)
    private Integer ranking;

    @NotNull (groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Column (name = "`status`", columnDefinition = "char(1)", nullable = false)
    private String status;

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
