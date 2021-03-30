package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.UserInfoParamDto;
import kr.co.metasoft.ito.api.common.entity.UserInfoEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface UserInfoMapper {


    public List<UserInfoEntity> selectUserInfoList(
            @Param (value = "userInfoParamDto") UserInfoParamDto userInfoParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);


    public Integer selectUserInfoListCount(
            @Param (value = "userInfoParamDto") UserInfoParamDto userInfoParamDto);


    public UserInfoEntity selectUserInfo(
            @Param (value = "id") Long id);

    /*
     * public void save(UserInfoEntity userInfoEntity);
     *
     * public void update(UserInfoEntity userInfoEntity);
     *
     * public void deleteById(Long id);
     */




    /*
     * public List<PersonEntity> selectPersonList(
     *
     * @Param (value = "personParamDto") PersonParamDto personParamDto,
     *
     * @Param (value = "pageRequest") PageRequest pageRequest);
     *
     * public Integer selectPersonListCount(
     *
     * @Param (value = "personParamDto") PersonParamDto personParamDto);
     *
     * public PersonEntity selectPerson(
     *
     * @Param (value = "id") Long id);
     *
     */


}
