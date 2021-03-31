package kr.co.metasoft.ito.api.app.dto;

import kr.co.metasoft.ito.api.common.dto.PersonDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private PersonDto personDto;

    private List<String> sectorList;

    private List<String> skillList;

    private List<String> languageList;
}
