package kr.co.metasoft.ito.api.common.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

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
public class ProjectPersonParamDto{

    private Long projectId;

    private Long personId;

    private String status;

}