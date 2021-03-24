package kr.co.metasoft.ito.api.common.entity.id;

import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PersonCareerEntityId implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(groups = {RemoveValidationGroup.class})
    private Long personId;

    @NotNull(groups = {RemoveValidationGroup.class})
    private Long careerId;
}
