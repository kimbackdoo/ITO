package kr.co.metasoft.ito.api.common.profile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import kr.co.metasoft.ito.api.common.profile.service.CommonProfileService;
import kr.co.metasoft.ito.common.entity.jpa.ProfileEntity;

@RestController
@RequestMapping(path = "api/common/profile")
public class CommonProfileController {
    @Autowired
    private CommonProfileService commonProfileService;

    @GetMapping(path = "")
    public Page<ProfileEntity> getProfileList(
            @ModelAttribute ProfileEntity profileEntity,
            @PageableDefault Pageable pageable) {
        return commonProfileService.getProfileList(profileEntity, pageable);
    }

    @GetMapping(path = "{id}")
    public ProfileEntity getProfile(
            @PathVariable(name = "id") Long id) {
        return commonProfileService.getProfile(id);
    }

    @PostMapping(path = "")
    public ProfileEntity createProfile(
            @RequestBody ProfileEntity profileEntity) {
        return commonProfileService.createProfile(profileEntity);
    }

    @PutMapping(path = "{id}")
    public ProfileEntity modifyProfile(
            @PathVariable(name = "id") Long id,
            @RequestBody ProfileEntity profileEntity) {
        profileEntity.setId(id);
        return commonProfileService.modifyProfile(profileEntity);
    }

    @DeleteMapping(path = "{id}")
    public void removeProfile(
            @PathVariable(name = "id") Long id) {
        commonProfileService.removeProfile(id);
    }
}
