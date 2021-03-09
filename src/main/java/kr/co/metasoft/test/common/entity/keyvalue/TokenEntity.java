package kr.co.metasoft.test.common.entity.keyvalue;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import lombok.Getter;
import lombok.Setter;

@KeySpace
@Getter
@Setter
public class TokenEntity {

    @Id
    private String token;

    private String username;

    private LocalDateTime timestamp;

}