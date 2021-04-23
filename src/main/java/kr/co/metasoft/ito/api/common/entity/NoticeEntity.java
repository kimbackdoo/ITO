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

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`tb_notice`")
@EntityListeners(value = {AuditingEntityListener.class})
public class NoticeEntity {


    @NotNull(groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Id
    @Column(name = "`id`", columnDefinition="bigint(20)")
    private Long id;

    @NotNull(groups = {ModifyValidationGroup.class})
    @Column(name = "`user_id`", columnDefinition="bigint(20)")
    private Long userId;

    @Column(name = "`title`", columnDefinition = "varchar(100)")
    private String title;

    @Column(name = "`contents`", columnDefinition = "varchar(100)")
    private String contents;

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
