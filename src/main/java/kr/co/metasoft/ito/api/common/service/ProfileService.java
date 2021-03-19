package kr.co.metasoft.ito.api.common.service;

import kr.co.metasoft.ito.api.common.dto.ProfileDto;
import kr.co.metasoft.ito.api.common.mapper.ProfileMapper;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.metasoft.ito.api.common.entity.ProfileEntity;
import kr.co.metasoft.ito.api.common.repository.ProfileRepository;

import javax.validation.Valid;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    @Transactional
    public PageResponse<ProfileEntity> getProfileList(
            @Valid ProfileDto profileDto,
            PageRequest pageRequest) {
        Integer profileListCount = profileMapper.selectProfileListCount(profileDto);
        List<ProfileEntity> profileList = profileMapper.selectProfileList(profileDto, pageRequest);
        PageResponse<ProfileEntity> pageResponse = new PageResponse<>(pageRequest, profileListCount);
        pageResponse.setItems(profileList);
        return pageResponse;
    }

    @Transactional
    public ProfileEntity getProfile(Long userProfId) {
        return profileRepository.findById(userProfId).orElse(null);
    }

    @Transactional
    public ProfileEntity createProfile(ProfileEntity profileEntity) {
        return profileRepository.save(profileEntity);
    }

    @Transactional
    public ProfileEntity modifyProfile(ProfileEntity profileEntity) {
        return profileRepository.save(profileEntity);
    }

    @Transactional
    public void removeProfileList(List<Long> idList) { profileMapper.deleteProfileList(idList); }
}
