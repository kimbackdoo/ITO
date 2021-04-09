package kr.co.metasoft.ito.api.app.dto;

import kr.co.metasoft.ito.api.common.dto.CareerDto;
import kr.co.metasoft.ito.api.common.dto.ProjectPersonDto;
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
public class InvolvementDto {

    private ProjectPersonDto projectPersonDto;

    private CareerDto careerDto;

}
