package kr.co.metasoft.ito.api.common.entity;

import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "`tb_person_career`")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonCareerEntity {
    @NotNull(groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Id
    @Column(name = "`person_id`", columnDefinition = "bigint(20)")
    private Long personId;

    @NotNull(groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Column(name = "`career_id`", columnDefinition = "bigint(20)")
    private Long careerId;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "`person_id`", referencedColumnName = "`id`", insertable = false, updatable = false)
    private PersonEntity person;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "`career_id`", referencedColumnName = "`person_career_id`", insertable = false, updatable = false)
    private CareerEntity career;
}
