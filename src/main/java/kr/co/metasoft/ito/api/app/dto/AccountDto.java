package kr.co.metasoft.ito.api.app.dto;

import kr.co.metasoft.ito.api.common.dto.PersonDto;
import kr.co.metasoft.ito.api.common.dto.UserDto;
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
public class AccountDto {

    private Long userId;

    private Long personId;

    private Long roleId;

    private UserDto userDto;

    private PersonDto personDto;
}
