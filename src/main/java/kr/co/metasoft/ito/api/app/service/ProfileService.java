package kr.co.metasoft.ito.api.app.service;

import kr.co.metasoft.ito.api.app.dto.ProfileDto;
import kr.co.metasoft.ito.api.common.dto.PersonLanguageDto;
import kr.co.metasoft.ito.api.common.dto.PersonSectorDto;
import kr.co.metasoft.ito.api.common.dto.PersonSkillDto;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.entity.PersonLanguageEntity;
import kr.co.metasoft.ito.api.common.entity.PersonSectorEntity;
import kr.co.metasoft.ito.api.common.entity.PersonSkillEntity;
import kr.co.metasoft.ito.api.common.repository.PersonLanguageRepository;
import kr.co.metasoft.ito.api.common.repository.PersonRepository;
import kr.co.metasoft.ito.api.common.repository.PersonSectorRepository;
import kr.co.metasoft.ito.api.common.repository.PersonSkillRepository;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;

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
    private PersonRepository personRepository;

    @Autowired
    PersonSectorRepository personSectorRepository;

    @Autowired
    PersonSkillRepository personSkillRepository;

    @Autowired
    PersonLanguageRepository personLanguageRepository;

    @Validated(value = {ModifyValidationGroup.class})
    @Transactional
    public void modifyPerson(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) ProfileDto personDto) {
        PersonEntity personEntity = PersonEntity.builder()
                .name(personDto.getName())
                .phoneNumber(personDto.getPhoneNumber())
                .jobType(personDto.getJobType())
                .inputStatus(personDto.getInputStatus())
                .certificateStatus(personDto.getCertificateStatus())
                .role(personDto.getRole())
                .career(personDto.getCareer())
                .pay(personDto.getPay())
                .workableDay(personDto.getWorkableDay())
                .postcode(personDto.getPostcode())
                .address(personDto.getAddress())
                .detailAddress(personDto.getDetailAddress())
                .email(personDto.getEmail())
                .website(personDto.getWebsite())
                .education(personDto.getEducation())
                .birthDate(personDto.getBirthDate())
                .build();

        Long id = personRepository.save(personEntity).getId();

        List<PersonSectorEntity> personSectorEntityList = new ArrayList<>();
        List<PersonSkillEntity> personSkillEntityList = new ArrayList<>();
        List<PersonLanguageEntity> personLanguageEntityList = new ArrayList<>();

//        for(PersonSectorDto dto : personDto.getSectorDtoList()) {
//            dto.setPersonId(id);
//            personSectorEntityList.add(PersonSectorEntity.builder()
//                    .sector(dto.getSector())
//                    .personId(id)
//                    .build()
//            );
//        }

        for(PersonSkillDto dto : personDto.getSkillDtoList()) {
            dto.setPersonId(id);
            personSkillEntityList.add(PersonSkillEntity.builder()
                    .skill(dto.getSkill())
                    .personId(id)
                    .build()
            );
        }

//        for(PersonLanguageDto dto : personDto.getLanguageDtoList()) {
//            dto.setPersonId(id);
//            personLanguageEntityList.add(PersonLanguageEntity.builder()
//                    .language(dto.getLanguage())
//                    .personId(id)
//                    .build()
//            );
//        }

//        personSectorRepository.saveAll(personSectorEntityList);
        personSkillRepository.saveAll(personSkillEntityList);
//        personLanguageRepository.saveAll(personLanguageEntityList);
    }
}
