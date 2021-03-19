package kr.co.metasoft.ito.api.common.dto;

import lombok.*;

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

    private String term;

    private String place;

    private String prsnl;

    private String status;

    private String salary;
}
