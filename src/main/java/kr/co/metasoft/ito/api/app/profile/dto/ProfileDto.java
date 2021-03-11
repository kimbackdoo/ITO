package kr.co.metasoft.ito.api.app.profile.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    private String name;

    private String job;

    private String skill;

    private String birth;

    private String career;

    private String email;
}
