package kr.co.metasoft.ito.api.common.entity.id;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class PersonSkillEntityId implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull (groups = {RemoveValidationGroup.class})
    private Long personId;

    @NotNull (groups = {RemoveValidationGroup.class})
    private String skill;

}