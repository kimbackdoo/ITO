package kr.co.metasoft.ito.api.common.dto;

import lombok.*;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Long id;

    private String address;

    private String number;

    private String email;

    private String skill;

    private String career;

    private String status;

    private String pay;

    private String name;

    private LocalDate birth;

    private String job;
}
