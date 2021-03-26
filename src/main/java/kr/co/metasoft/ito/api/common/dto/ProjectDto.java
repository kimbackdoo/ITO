package kr.co.metasoft.ito.api.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private Long adminProjId;

    private String name;

    private String job;

    private String skill;

    private String career;

    private String degree;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sterm;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eterm;

    private String place;

    private Integer prsnl;

    private String status;

    private Long salary;

    private String term;
}
