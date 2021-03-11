package kr.co.metasoft.ito.api.common.profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.metasoft.ito.common.entity.jpa.ProfileEntity;
import kr.co.metasoft.ito.common.repository.jpa.ProfileRepository;

@Service
public class CommonProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Transactional
    public Page<ProfileEntity> getProfileList(
            ProfileEntity profileEntity, Pageable pageable) {
        return profileRepository.findAll(Example.of(profileEntity), pageable);
    }

    @Transactional
    public ProfileEntity getProfile(Long id) {
        return profileRepository.findById(id).orElse(null);
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
    public void removeProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
