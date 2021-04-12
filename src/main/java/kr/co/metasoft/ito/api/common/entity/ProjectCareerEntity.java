package kr.co.metasoft.ito.api.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.co.metasoft.ito.api.common.entity.id.ProjectCareerEntityId;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name = "`tb_project_career`")
@IdClass (value = ProjectCareerEntityId.class)
@EntityListeners (value = {AuditingEntityListener.class})
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCareerEntity {

    @NotNull (groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Id
    @Column (name = "`project_id`", columnDefinition = "bigint(20)")
    private Long projectId;

    @NotNull (groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Id
    @Column (name = "`career_id`", columnDefinition = "bigint(20)")
    private Long careerId;

    @ManyToOne
    @NotFound (action = NotFoundAction.IGNORE)
    @JoinColumn (name = "`project_id`", referencedColumnName = "`id`", insertable = false, updatable = false)
    private ProjectEntity project;

    @ManyToOne
    @NotFound (action = NotFoundAction.IGNORE)
    @JoinColumn (name = "`career_id`", referencedColumnName = "`person_career_id`", insertable = false, updatable = false)
    private CareerEntity career;



}
