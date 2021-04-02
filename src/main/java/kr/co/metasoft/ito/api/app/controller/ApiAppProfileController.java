package kr.co.metasoft.ito.api.app.controller;

import kr.co.metasoft.ito.api.app.dto.ProfileDto;
import kr.co.metasoft.ito.api.app.service.ProfileService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/app/profile")
public class ApiAppProfileController {
    @Autowired
    ProfileService profileService;

    @PostMapping(path = "")
    public ProfileDto createProfile(@RequestBody ProfileDto profileDto) {
        return profileService.createProfile(profileDto);
    }

    @PutMapping(path = "")
    public void modifyProfile(@RequestBody ProfileDto personDto) { profileService.modifyProfile(personDto); }

    @DeleteMapping(path = "{id}")
    public void removeProfile(@PathVariable Long id) { profileService.removeProfile(id); }

    @DeleteMapping(path ="")
    public void removeProfileList(@RequestBody List<ProfileDto> profileDtoList) { profileService.removeProfileList(profileDtoList); }

}
