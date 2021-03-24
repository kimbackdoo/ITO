package kr.co.metasoft.ito.api.common.dto;

import lombok.*;

import javax.persistence.Transient;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonCareerDto {
    private Long personId;

    private Long careerId;

    @Transient
    private PersonDto personDto;

    @Transient
    private CareerDto careerDto;
}
