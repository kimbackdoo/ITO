package kr.co.metasoft.ito.api.app.service;

import kr.co.metasoft.ito.api.app.dto.ProfileDto;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.entity.PersonLanguageEntity;
import kr.co.metasoft.ito.api.common.entity.PersonSectorEntity;
import kr.co.metasoft.ito.api.common.entity.PersonSkillEntity;
import kr.co.metasoft.ito.api.common.repository.PersonLanguageRepository;
import kr.co.metasoft.ito.api.common.repository.PersonRepository;
import kr.co.metasoft.ito.api.common.repository.PersonSectorRepository;
import kr.co.metasoft.ito.api.common.repository.PersonSkillRepository;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;

import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonSectorRepository personSectorRepository;

    @Autowired
    PersonSkillRepository personSkillRepository;

    @Autowired
    PersonLanguageRepository personLanguageRepository;

    @Validated(value = {ModifyValidationGroup.class})
    @Transactional
    public void modifyPerson(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) ProfileDto profileDto) {
        PersonEntity.builder()
                .id(profileDto.getPersonDto().getId())
                .name(profileDto.getPersonDto().getName())
                .phoneNumber(profileDto.getPersonDto().getPhoneNumber())
                .jobType(profileDto.getPersonDto().getJobType())
                .inputStatus(profileDto.getPersonDto().getInputStatus())
                .certificateStatus(profileDto.getPersonDto().getCertificateStatus())
                .role(profileDto.getPersonDto().getRole())
                .career(profileDto.getPersonDto().getCareer())
                .pay(profileDto.getPersonDto().getPay())
                .workableDay(profileDto.getPersonDto().getWorkableDay())
                .postcode(profileDto.getPersonDto().getPostcode())
                .address(profileDto.getPersonDto().getAddress())
                .detailAddress(profileDto.getPersonDto().getDetailAddress())
                .email(profileDto.getPersonDto().getEmail())
                .website(profileDto.getPersonDto().getWebsite())
                .education(profileDto.getPersonDto().getEducation())
                .birthDate(profileDto.getPersonDto().getBirthDate())
                .build();

        Long id = profileDto.getPersonDto().getId();

        List<PersonSectorEntity> personSectorEntityList = new ArrayList<>();
        List<PersonSkillEntity> personSkillEntityList = new ArrayList<>();
        List<PersonLanguageEntity> personLanguageEntityList = new ArrayList<>();

        personSectorRepository.deleteSectorAllByPersonId(id);
        for(String sector : profileDto.getSectorList()) {
            personSectorEntityList.add(PersonSectorEntity.builder()
                    .sector(sector)
                    .personId(id)
                    .build()
            );
        }

        personSkillRepository.deleteSkillAllByPersonId(id);
        for(String skill : profileDto.getSkillList()) {
            personSkillEntityList.add(PersonSkillEntity.builder()
                    .skill(skill)
                    .personId(id)
                    .build()
            );
        }

        personLanguageRepository.deleteLanguageAllByPersonId(id);
        for(String language : profileDto.getLanguageList()) {
            personLanguageEntityList.add(PersonLanguageEntity.builder()
                    .language(language)
                    .personId(id)
                    .build()
            );
        }

        personSectorRepository.saveAll(personSectorEntityList);
        personSkillRepository.saveAll(personSkillEntityList);
        personLanguageRepository.saveAll(personLanguageEntityList);
    }

    @Validated(value = {RemoveValidationGroup.class})
    @Transactional
    public void removeProfile(
            @Valid @NotNull(groups = {RemoveValidationGroup.class}) ProfileDto profileDto) {
        personRepository.delete(PersonEntity.builder().id(profileDto.getPersonDto().getId()).build());
        personSectorRepository.delete(PersonSectorEntity.builder().personId(profileDto.getPersonDto().getId()).build());
        personSkillRepository.delete(PersonSkillEntity.builder().personId(profileDto.getPersonDto().getId()).build());
        personLanguageRepository.delete(PersonLanguageEntity.builder().personId(profileDto.getPersonDto().getId()).build());
    }
}
