package kr.co.metasoft.ito.api.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.common.dto.UserInfoDto;
import kr.co.metasoft.ito.api.common.dto.UserInfoParamDto;
import kr.co.metasoft.ito.api.common.entity.UserInfoEntity;
import kr.co.metasoft.ito.api.common.service.UserInfoService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@RestController
@RequestMapping(path = "/api/common/user-info")
public class ApiCommonUserInfoController {

        @Autowired
        private UserInfoService userInfoService;

        @GetMapping (path = "")
        public PageResponse<UserInfoEntity> getUserInfoList(
                @ModelAttribute UserInfoParamDto userInfoParamDto,
                @ModelAttribute PageRequest pageRequest) {
            System.out.println(userInfoParamDto.getName());
            System.out.println(userInfoParamDto.getJobType());
            System.out.println(userInfoParamDto.getCareer());
            System.out.println(userInfoParamDto.getPay());
            System.out.println(userInfoParamDto.getInputStatus());
            System.out.println(pageRequest.toString());
            return userInfoService.getUserInfoList(userInfoParamDto, pageRequest);
        }

        @GetMapping (path = "{id}")
        public UserInfoEntity getUserInfo(
                @PathVariable (name = "id") Long id) {
            return userInfoService.getUserInfo(id);
        }

        @PostMapping (path = "", params = {"bulk"})
        public List<UserInfoEntity> createUserInfoList(
                @RequestBody List<UserInfoDto> userInfoDtoList) {
            List<UserInfoEntity> userInfoList = new ArrayList<>();
            for (int i = 0; i < userInfoDtoList.size(); i++) {
                UserInfoDto userInfoDto = userInfoDtoList.get(i);
                UserInfoEntity userInfoEntity = UserInfoEntity.builder()
                        .name(userInfoDto.getName())
                        .number(userInfoDto.getNumber())
                        .jobType(userInfoDto.getJobType())
                        .skill(userInfoDto.getSkill())
                        .birthDate(userInfoDto.getBirthDate())
                        .career(userInfoDto.getCareer())
                        .pay(userInfoDto.getPay())
                        .address(userInfoDto.getAddress())
                        .inputStatus(userInfoDto.getInputStatus())
                        .workableDay(userInfoDto.getWorkableDay())
                        .build();
                userInfoList.add(userInfoEntity);
            }
            return userInfoService.createUserInfoList(userInfoList);
        }

        @PostMapping (path = "", params = {"!bulk"})
        public UserInfoEntity createUserInfo(
                @RequestBody UserInfoDto userInfoDto) {
            UserInfoEntity userInfoEntity = UserInfoEntity.builder()
                    .name(userInfoDto.getName())
                    .number(userInfoDto.getNumber())
                    .jobType(userInfoDto.getJobType())
                    .skill(userInfoDto.getSkill())
                    .birthDate(userInfoDto.getBirthDate())
                    .career(userInfoDto.getCareer())
                    .pay(userInfoDto.getPay())
                    .address(userInfoDto.getAddress())
                    .inputStatus(userInfoDto.getInputStatus())
                    .workableDay(userInfoDto.getWorkableDay())
                    .build();
            return userInfoService.createUserInfo(userInfoEntity);
        }

        @PutMapping (path = "")
        public List<UserInfoEntity> modifyUserInfoList(
                @RequestBody List<UserInfoDto> userInfoDtoList) {
            List<UserInfoEntity> userInfoList = new ArrayList<>();
            for (int i = 0; i < userInfoDtoList.size(); i++) {
                UserInfoDto userInfoDto = userInfoDtoList.get(i);
                UserInfoEntity userInfoEntity = UserInfoEntity.builder()
                        .id(userInfoDto.getId())
                        .name(userInfoDto.getName())
                        .number(userInfoDto.getNumber())
                        .jobType(userInfoDto.getJobType())
                        .skill(userInfoDto.getSkill())
                        .birthDate(userInfoDto.getBirthDate())
                        .career(userInfoDto.getCareer())
                        .pay(userInfoDto.getPay())
                        .address(userInfoDto.getAddress())
                        .inputStatus(userInfoDto.getInputStatus())
                        .workableDay(userInfoDto.getWorkableDay())
                        .build();
                userInfoList.add(userInfoEntity);
            }
            return userInfoService.modifyUserInfoList(userInfoList);
        }


        @PutMapping (path = "{id}")
        public UserInfoEntity modifyUserInfo(
                @PathVariable (name = "id") Long id,
                @RequestBody UserInfoDto userInfoDto) {
            UserInfoEntity userInfoEntity = UserInfoEntity.builder()
                    .id(id)
                    .name(userInfoDto.getName())
                    .number(userInfoDto.getNumber())
                    .jobType(userInfoDto.getJobType())
                    .skill(userInfoDto.getSkill())
                    .birthDate(userInfoDto.getBirthDate())
                    .career(userInfoDto.getCareer())
                    .pay(userInfoDto.getPay())
                    .address(userInfoDto.getAddress())
                    .inputStatus(userInfoDto.getInputStatus())
                    .workableDay(userInfoDto.getWorkableDay())
                    .build();
            return userInfoService.modifyUserInfo(userInfoEntity);
        }

        @DeleteMapping (path = "")
        public void removeUserInfoList(
                @RequestBody List<Long> idList) {
            userInfoService.removeUserInfoList(idList);;
        }

        @DeleteMapping (path = "{id}")
        public void removeUserInfo(
                @PathVariable (name = "id") Long id) {
            userInfoService.removeUserInfo(id);
        }





    }
