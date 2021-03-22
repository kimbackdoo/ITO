package kr.co.metasoft.ito.api.common.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String password;

    @JsonFormat (pattern = "yyyy-MM-dd")
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    private LocalDate lastModifiedPasswordDate;

    private String status;

    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    private String oldPassword;

    private String newPassword;

}