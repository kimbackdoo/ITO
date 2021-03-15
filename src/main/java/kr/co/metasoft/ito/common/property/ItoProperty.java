package kr.co.metasoft.ito.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@ConfigurationProperties (prefix = "ito")
@Getter
@Setter
@ToString
public class ItoProperty {

    private Jwt jwt;

    @Getter
    @Setter
    @ToString
    public static class Jwt {

        private String secretKey;

        private String subject;

        private Long expirationTimeMillis;

    }

}