package kr.co.metasoft.ito.api.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import kr.co.metasoft.ito.api.common.dto.UserInfoParamDto;
import kr.co.metasoft.ito.api.common.entity.UserInfoEntity;
import kr.co.metasoft.ito.api.common.mapper.UserInfoMapper;
import kr.co.metasoft.ito.api.common.repository.UserInfoRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

@Validated
@Service
public class UserInfoService {

       @Autowired
        private UserInfoRepository userInfoRepository;


       @Autowired
       private UserInfoMapper userInfoMapper;


       @Validated (value = {ReadValidationGroup.class})
       @Transactional(readOnly = true)
       public PageResponse<UserInfoEntity> getUserInfoList(
               @Valid UserInfoParamDto userInfoParamDto,
               PageRequest pageRequest) {
           Integer userInfoListCount = userInfoMapper.selectUserInfoListCount(userInfoParamDto);
           List<UserInfoEntity> userInfoList = userInfoMapper.selectUserInfoList(userInfoParamDto, pageRequest);
           PageResponse<UserInfoEntity> pageResponse = new PageResponse<>(pageRequest, userInfoListCount);
           pageResponse.setItems(userInfoList);
           return pageResponse;
       }


       @Validated (value = {ReadValidationGroup.class})
       @Transactional (readOnly = true)
       public UserInfoEntity getUserInfo(
               @Valid @NotNull (groups = {ReadValidationGroup.class}) Long id) {
           return userInfoMapper.selectUserInfo(id);
       }

       @Validated (value = {CreateValidationGroup.class})
       @Transactional
       public List<UserInfoEntity> createUserInfoList(
               @Valid @NotEmpty (groups = {CreateValidationGroup.class}) List<@NotNull (groups = {CreateValidationGroup.class}) UserInfoEntity> userInfoList) {
           return userInfoRepository.saveAll(userInfoList);
       }

       @Validated (value = {CreateValidationGroup.class})
       @Transactional
       public UserInfoEntity createUserInfo(
               @Valid @NotNull (groups = {CreateValidationGroup.class}) UserInfoEntity userInfoEntity) {
           return userInfoRepository.save(userInfoEntity);
       }

       @Validated (value = {ModifyValidationGroup.class})
       @Transactional
       public List<UserInfoEntity> modifyUserInfoList(
               @Valid @NotEmpty (groups = {ModifyValidationGroup.class}) List<@NotNull (groups = {ModifyValidationGroup.class}) UserInfoEntity> userInfoList) {
           return userInfoRepository.saveAll(userInfoList);
       }

       @Validated (value = {ModifyValidationGroup.class})
       @Transactional
       public UserInfoEntity modifyUserInfo(
               @Valid @NotNull (groups = {ModifyValidationGroup.class}) UserInfoEntity userInfoEntity) {
           return userInfoRepository.save(userInfoEntity);
       }

       @Validated (value = {RemoveValidationGroup.class})
       @Transactional
       public void removeUserInfoList(
               @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) Long> idList) {
           List<UserInfoEntity> userInfoList = new ArrayList<>();
           for (int i = 0; i < idList.size(); i++) {
               Long id = idList.get(i);
               userInfoList.add(UserInfoEntity.builder().id(id).build());
           }
           userInfoRepository.deleteAll(userInfoList);
       }

       @Validated (value = {RemoveValidationGroup.class})
       @Transactional
       public void removeUserInfo(
               @Valid @NotNull (groups = {RemoveValidationGroup.class}) Long id) {
           userInfoRepository.delete(UserInfoEntity.builder().id(id).build());
       }




}
