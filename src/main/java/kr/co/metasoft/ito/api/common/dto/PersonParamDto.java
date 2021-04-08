package kr.co.metasoft.ito.api.common.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonParamDto {

    private Long id;

    private String name;

    private String phoneNumber;

    private String jobType;

    private List<String> memo;

    private String inputStatus;

    private String certificateStatus;

    private String skill;

    private String career;

    private String days;

    private Long minPay;

    private Long maxPay;

    private String local;

    private String detailLocal;

    @JsonFormat (pattern = "yyyy-MM-dd")
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    private LocalDate workableDay;

    private Long postcode;

    private String address;

    private String detailAddress;

    private String email;

    private String website;

    private String education;

    @JsonFormat (pattern = "yyyy-MM-dd")
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    private LocalDate startBirthDate;

    @JsonFormat (pattern = "yyyy-MM-dd")
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    private LocalDate endBirthDate;

    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;


}