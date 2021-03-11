package kr.co.metasoft.ito.api.app.user.dto;

import lombok.Setter;
import kr.co.metasoft.ito.common.entity.jpa.PersonEntity;
import kr.co.metasoft.ito.common.entity.jpa.UserEntity;
import lombok.Getter;

@Getter
@Setter
public class UserPersonDto {

    private UserEntity user;

    private PersonEntity person;

}