package kr.co.metasoft.ito.common.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestDto {

    @NotNull
    private String validNotNull;

    @NotNull (groups = {Create.class})
    private String validNotNullCreate;

    @NotNull (groups = {Update.class})
    private String validNotNullUpdate;

    public static interface Create {}

    public static interface Update {}

}