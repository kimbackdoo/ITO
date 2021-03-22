package kr.co.metasoft.ito.api.common.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectParamDto {
    private String nameLike;

    private String job;

    private String career;

    private String degree;

    private String termLike;

    private String place;

    private String prsnl;

    private String status;

    private String salary;
}
