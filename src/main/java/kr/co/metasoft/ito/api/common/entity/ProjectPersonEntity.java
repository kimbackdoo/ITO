package kr.co.metasoft.ito.api.common.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.co.metasoft.ito.api.common.entity.id.ProjectPersonEntityId;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name = "`tb_project_person`")
@IdClass (value = ProjectPersonEntityId.class)
@EntityListeners (value = {AuditingEntityListener.class})
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPersonEntity {

    @NotNull (groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Id
    @Column (name = "`project_id`", columnDefinition = "bigint(20)")
    private Long projectId;

    @NotNull (groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Id
    @Column (name = "`person_id`", columnDefinition = "bigint(20)")
    private Long personId;


    @NotNull (groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Column (name = "`status`", columnDefinition = "varchar(100)")
    private String status;


    @ManyToOne
    @NotFound (action = NotFoundAction.IGNORE)
    @JoinColumn (name = "`project_id`", referencedColumnName = "`id`", insertable = false, updatable = false)
    private ProjectEntity project;

    @ManyToOne
    @NotFound (action = NotFoundAction.IGNORE)
    @JoinColumn (name = "`person_id`", referencedColumnName = "`id`", insertable = false, updatable = false)
    private PersonEntity person;
}
