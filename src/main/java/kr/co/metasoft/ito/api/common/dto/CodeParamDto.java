package kr.co.metasoft.ito.api.common.dto;

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
public class CodeParamDto {

    private String parentId;

    private String idStartLike;

    private String nameLike;

    private String status;

    private String skill;

    private String detailLocal;
}
