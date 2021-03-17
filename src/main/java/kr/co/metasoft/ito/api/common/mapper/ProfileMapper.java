package kr.co.metasoft.ito.api.common.mapper;

import kr.co.metasoft.ito.api.common.dto.ProfileDto;
import kr.co.metasoft.ito.api.common.entity.ProfileEntity;
import kr.co.metasoft.ito.common.util.PageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProfileMapper {
    Integer selectProfileListCount(
            @Param(value = "profileDto") ProfileDto profileDto);

    List<ProfileEntity> selectProfileList(
            @Param(value = "profileDto") ProfileDto profileDto,
            @Param(value = "pageRequest") PageRequest pageRequest);

    void deleteProfileList(@Param(value = "list") List<Long> profileDtoList);
}
