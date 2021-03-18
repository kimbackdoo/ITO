package kr.co.metasoft.ito.api.common.controller;

import kr.co.metasoft.ito.api.common.dto.ProfileDto;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kr.co.metasoft.ito.api.common.service.ProfileService;
import kr.co.metasoft.ito.api.common.entity.ProfileEntity;

import java.util.List;

@RestController
@RequestMapping(path = "api/common/profiles")
public class ApiCommonProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping(path = "")
    public PageResponse<ProfileEntity> getProfileList(
            @ModelAttribute ProfileDto profileDto,
            @ModelAttribute PageRequest pageRequest) {
        return profileService.getProfileList(profileDto, pageRequest);
    }

    @GetMapping(path = "{userProfId}")
    public ProfileEntity getProfile(
            @PathVariable(name = "userProfId") Long userProfId) {
        return profileService.getProfile(userProfId);
    }

    @PostMapping(path = "")
    public ProfileEntity createProfile(
            @RequestBody ProfileEntity profileEntity) {
        return profileService.createProfile(profileEntity);
    }

    @PutMapping(path = "{userProfId}")
    public ProfileEntity modifyProfile(
            @PathVariable(name = "userProfId") Long userProfId,
            @RequestBody ProfileEntity profileEntity) {
        profileEntity.setUserProfId(userProfId);
        return profileService.modifyProfile(profileEntity);
    }

    @DeleteMapping(path = "")
    public void removeProfileList(
            @RequestBody List<Long> idList) {
        profileService.removeProfileList(idList);
        System.out.println(idList);
    }
}
