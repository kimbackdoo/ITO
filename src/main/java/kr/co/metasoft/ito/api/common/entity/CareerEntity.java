package kr.co.metasoft.ito.api.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_career")
@EntityListeners(value = {AuditingEntityListener.class})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CareerEntity {
    @Null (groups = {CreateValidationGroup.class})
    @NotNull (groups = {ModifyValidationGroup.class})
    @Id
    @Column(name = "person_career_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personCareerId;

    @NotNull
    @Column(name = "`person_id`", columnDefinition = "bigint(20)")
    private Long personId;

    @Column(name = "name")
    private String name;

    @Column(name = "start_period")
    private LocalDate startPeriod;

    @Column(name = "end_period")
    private LocalDate endPeriod;

    @Column(name = "position")
    private String position;

    @Column(name = "task")
    private String task;

    @Column(name = "pay")
    private String pay;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_dt", columnDefinition = "datetime", nullable = false, updatable = false)
    private LocalDateTime createDt;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modify_dt", columnDefinition = "datetime", nullable = false)
    private LocalDateTime modifyDt;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "`person_id`", referencedColumnName = "`id`", insertable = false, updatable = false)
    private PersonEntity person;
}
