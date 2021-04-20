package kr.co.metasoft.ito.api.common.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VacationParamDto {

    private Long id;

    private Long userId;

    private String department;

    private String emergencyNum;

    private String type;

    private LocalDate sterm;

    private LocalDate eterm;

    private String detail;

}
