package kr.co.metasoft.ito.api.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.metasoft.ito.api.common.dto.PersonLanguageDto;
import kr.co.metasoft.ito.api.common.dto.PersonSectorDto;
import kr.co.metasoft.ito.api.common.dto.PersonSkillDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Long id;

    private String name;

    private String phoneNumber;

    private String jobType;

    private String inputStatus;

    private String certificateStatus;

    private String role;

    private String career;

    private Long pay;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workableDay;

    private String postcode;

    private String address;

    private String detailAddress;

    private String email;

    private String website;

    private String education;

    @JsonFormat (pattern = "yyyy-MM-dd")
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    private List<PersonSectorDto> sectorDtoList;

    private List<PersonSkillDto> skillDtoList;

    private List<PersonLanguageDto> languageDtoList;
}
