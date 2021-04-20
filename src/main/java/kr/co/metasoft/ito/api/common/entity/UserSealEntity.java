package kr.co.metasoft.ito.api.common.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "`tb_seal`")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserSealEntity {


    @NotNull(groups = {CreateValidationGroup.class, ModifyValidationGroup.class})
    @Id
    @Column(name = "`user_seal_id`", columnDefinition="bigint(20)")
    private Long userSealId;


    @Column(name = "`image`", columnDefinition = "blob", nullable = true)
    private Blob image;

}
