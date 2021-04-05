package kr.co.metasoft.ito.api.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import kr.co.metasoft.ito.api.app.dto.AccountDto;
import kr.co.metasoft.ito.api.common.dto.PersonLanguageDto;
import kr.co.metasoft.ito.api.common.dto.PersonSectorDto;
import kr.co.metasoft.ito.api.common.dto.PersonSkillDto;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.entity.PersonLanguageEntity;
import kr.co.metasoft.ito.api.common.entity.PersonSectorEntity;
import kr.co.metasoft.ito.api.common.entity.PersonSkillEntity;
import kr.co.metasoft.ito.api.common.entity.RoleUserEntity;
import kr.co.metasoft.ito.api.common.entity.UserEntity;
import kr.co.metasoft.ito.api.common.entity.UserPersonEntity;
import kr.co.metasoft.ito.api.common.repository.PersonLanguageRepository;
import kr.co.metasoft.ito.api.common.repository.PersonRepository;
import kr.co.metasoft.ito.api.common.repository.PersonSectorRepository;
import kr.co.metasoft.ito.api.common.repository.PersonSkillRepository;
import kr.co.metasoft.ito.api.common.repository.RoleUserRepository;
import kr.co.metasoft.ito.api.common.repository.UserPersonRepository;
import kr.co.metasoft.ito.api.common.repository.UserRepository;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

@Service
public class AccountService {

    @Autowired
    private UserPersonRepository userPersonRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    PersonSectorRepository personSectorRepository;

    @Autowired
    PersonSkillRepository personSkillRepository;

    @Autowired
    PersonLanguageRepository personLanguageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public AccountDto createAccount (
            @Valid @NotNull (groups = {CreateValidationGroup.class}) AccountDto accountDto) {
        AccountDto result = new AccountDto();
        UserEntity userEntity = UserEntity.builder()
                .username(accountDto.getUserDto().getUsername())
                .password(accountDto.getUserDto().getPassword())
                .lastModifiedPasswordDate(LocalDate.now())
                .status(accountDto.getUserDto().getStatus())
                .build();

        PersonEntity personEntity = PersonEntity.builder()
                .name(accountDto.getPersonDto().getName())
                .phoneNumber(accountDto.getPersonDto().getPhoneNumber())
                .jobType(accountDto.getPersonDto().getJobType())
                .inputStatus(accountDto.getPersonDto().getInputStatus())
                .certificateStatus(accountDto.getPersonDto().getCertificateStatus())
                .role(accountDto.getPersonDto().getRole())
                .career(accountDto.getPersonDto().getCareer())
                .minPay(accountDto.getPersonDto().getMinPay())
                .maxPay(accountDto.getPersonDto().getMaxPay())
                .workableDay(accountDto.getPersonDto().getWorkableDay())
                .postcode(accountDto.getPersonDto().getPostcode())
                .address(accountDto.getPersonDto().getAddress())
                .detailAddress(accountDto.getPersonDto().getDetailAddress())
                .email(accountDto.getPersonDto().getEmail())
                .website(accountDto.getPersonDto().getWebsite())
                .education(accountDto.getPersonDto().getEducation())
                .birthDate(accountDto.getPersonDto().getBirthDate())
                .build();

        userEntity.setPassword(passwordEncoder.encode((userEntity.getPassword())));
        Long userId = userRepository.save(userEntity).getId();
        Long personId = personRepository.save(personEntity).getId();
        result.setUserId(userId);
        result.setPersonId(personId);

        List<PersonSectorEntity> personSectorEntityList = new ArrayList<>();
        List<PersonSkillEntity> personSkillEntityList = new ArrayList<>();
        List<PersonLanguageEntity> personLanguageEntityList = new ArrayList<>();

        for(PersonSectorDto dto : accountDto.getPersonSectorDtoList()) {
            dto.setPersonId(personId);
            personSectorEntityList.add(PersonSectorEntity.builder()
                    .sector(dto.getSector())
                    .personId(personId)
                    .build()
            );
        }
        result.setPersonSectorDtoList(accountDto.getPersonSectorDtoList());

        for(PersonSkillDto dto : accountDto.getPersonSkillDtoList()) {
            dto.setPersonId(personId);
            personSkillEntityList.add(PersonSkillEntity.builder()
                    .skill(dto.getSkill())
                    .personId(personId)
                    .build()
            );
        }
        result.setPersonSkillDtoList(accountDto.getPersonSkillDtoList());

        for(PersonLanguageDto dto : accountDto.getPersonLanguageDtoList()) {
            dto.setPersonId(personId);
            personLanguageEntityList.add(PersonLanguageEntity.builder()
                    .language(dto.getLanguage())
                    .personId(personId)
                    .build()
            );
        }
        result.setPersonLanguageDtoList(accountDto.getPersonLanguageDtoList());

        UserPersonEntity userPersonEntity = new UserPersonEntity(userId, personId, null, null);
        RoleUserEntity roleUserEntity = new RoleUserEntity(userId, accountDto.getRoleId(), null, null);

        roleUserRepository.save(roleUserEntity);
        userPersonRepository.save(userPersonEntity);
        personSectorRepository.saveAll(personSectorEntityList);
        personSkillRepository.saveAll(personSkillEntityList);
        personLanguageRepository.saveAll(personLanguageEntityList);
        return result;
    }


    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public void modifyAccount(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) AccountDto accountDto) {
        UserEntity userEntity = UserEntity.builder()
                .id(accountDto.getUserDto().getId())
                .username(accountDto.getUserDto().getUsername())
                .password(accountDto.getUserDto().getPassword())
                .lastModifiedPasswordDate(accountDto.getUserDto().getLastModifiedPasswordDate())
                .status(accountDto.getUserDto().getStatus())
                .build();

        PersonEntity personEntity = PersonEntity.builder()
                .name(accountDto.getPersonDto().getName())
                .phoneNumber(accountDto.getPersonDto().getPhoneNumber())
                .jobType(accountDto.getPersonDto().getJobType())
                .inputStatus(accountDto.getPersonDto().getInputStatus())
                .certificateStatus(accountDto.getPersonDto().getCertificateStatus())
                .role(accountDto.getPersonDto().getRole())
                .career(accountDto.getPersonDto().getCareer())
                .minPay(accountDto.getPersonDto().getMinPay())
                .maxPay(accountDto.getPersonDto().getMaxPay())
                .workableDay(accountDto.getPersonDto().getWorkableDay())
                .postcode(accountDto.getPersonDto().getPostcode())
                .address(accountDto.getPersonDto().getAddress())
                .detailAddress(accountDto.getPersonDto().getDetailAddress())
                .email(accountDto.getPersonDto().getEmail())
                .website(accountDto.getPersonDto().getWebsite())
                .education(accountDto.getPersonDto().getEducation())
                .birthDate(accountDto.getPersonDto().getBirthDate())
                .build();

        Long userId = userRepository.save(userEntity).getId();
        Long personId = personRepository.save(personEntity).getId();

        List<PersonSectorEntity> personSectorEntityList = new ArrayList<>();
        List<PersonSkillEntity> personSkillEntityList = new ArrayList<>();
        List<PersonLanguageEntity> personLanguageEntityList = new ArrayList<>();

        for(PersonSectorDto dto : accountDto.getPersonSectorDtoList()) {
            dto.setPersonId(personId);
            personSectorEntityList.add(PersonSectorEntity.builder()
                    .sector(dto.getSector())
                    .personId(personId)
                    .build()
            );
        }

        for(PersonSkillDto dto : accountDto.getPersonSkillDtoList()) {
            dto.setPersonId(personId);
            personSkillEntityList.add(PersonSkillEntity.builder()
                    .skill(dto.getSkill())
                    .personId(personId)
                    .build()
            );
        }

        for(PersonLanguageDto dto : accountDto.getPersonLanguageDtoList()) {
            dto.setPersonId(personId);
            personLanguageEntityList.add(PersonLanguageEntity.builder()
                    .language(dto.getLanguage())
                    .personId(personId)
                    .build()
            );
        }

        UserPersonEntity userPersonEntity = new UserPersonEntity(userId, personId, null, null);
        RoleUserEntity roleUserEntity = new RoleUserEntity(userId, accountDto.getRoleId(), null, null);
        roleUserRepository.save(roleUserEntity);
        userPersonRepository.save(userPersonEntity);
        personSectorRepository.saveAll(personSectorEntityList);
        personSkillRepository.saveAll(personSkillEntityList);
        personLanguageRepository.saveAll(personLanguageEntityList);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public void userSignUp (
            @Valid @NotNull (groups = {CreateValidationGroup.class}) AccountDto accountDto) {

        UserEntity userEntity = UserEntity.builder()
                .id(accountDto.getUserDto().getId())
                .username(accountDto.getUserDto().getUsername())
                .password(accountDto.getUserDto().getPassword())
                .lastModifiedPasswordDate(LocalDate.now())
                .status("F")
                .build();

        PersonEntity personEntity = PersonEntity.builder()
                .name(accountDto.getPersonDto().getName())
                .phoneNumber(accountDto.getPersonDto().getPhoneNumber())
                .jobType(accountDto.getPersonDto().getJobType())
                .inputStatus(accountDto.getPersonDto().getInputStatus())
                .certificateStatus(accountDto.getPersonDto().getCertificateStatus())
                .role(accountDto.getPersonDto().getRole())
                .career(accountDto.getPersonDto().getCareer())
                .minPay(accountDto.getPersonDto().getMinPay())
                .maxPay(accountDto.getPersonDto().getMaxPay())
                .workableDay(accountDto.getPersonDto().getWorkableDay())
                .postcode(accountDto.getPersonDto().getPostcode())
                .address(accountDto.getPersonDto().getAddress())
                .detailAddress(accountDto.getPersonDto().getDetailAddress())
                .email(accountDto.getPersonDto().getEmail())
                .website(accountDto.getPersonDto().getWebsite())
                .education(accountDto.getPersonDto().getEducation())
                .birthDate(accountDto.getPersonDto().getBirthDate())
                .build();

        userEntity.setPassword(passwordEncoder.encode((userEntity.getPassword())));
        Long userId = userRepository.save(userEntity).getId();
        Long personId = personRepository.save(personEntity).getId();

        List<PersonSectorEntity> personSectorEntityList = new ArrayList<>();
        List<PersonSkillEntity> personSkillEntityList = new ArrayList<>();
        List<PersonLanguageEntity> personLanguageEntityList = new ArrayList<>();

        for(PersonSectorDto dto : accountDto.getPersonSectorDtoList()) {
            dto.setPersonId(personId);
            personSectorEntityList.add(PersonSectorEntity.builder()
                    .sector(dto.getSector())
                    .personId(personId)
                    .build()
            );
        }

        for(PersonSkillDto dto : accountDto.getPersonSkillDtoList()) {
            dto.setPersonId(personId);
            personSkillEntityList.add(PersonSkillEntity.builder()
                    .skill(dto.getSkill())
                    .personId(personId)
                    .build()
            );
        }

        for(PersonLanguageDto dto : accountDto.getPersonLanguageDtoList()) {
            dto.setPersonId(personId);
            personLanguageEntityList.add(PersonLanguageEntity.builder()
                    .language(dto.getLanguage())
                    .personId(personId)
                    .build()
            );
        }

        UserPersonEntity userPersonEntity = new UserPersonEntity();

        userPersonEntity.setUserId(userId);
        userPersonEntity.setPersonId(personId);

        RoleUserEntity roleUserEntity = new RoleUserEntity();

        roleUserEntity.setUserId(userId);
        roleUserEntity.setRoleId(Long.valueOf(2));

        roleUserRepository.save(roleUserEntity);
        userPersonRepository.save(userPersonEntity);
        personSectorRepository.saveAll(personSectorEntityList);
        personSkillRepository.saveAll(personSkillEntityList);
        personLanguageRepository.saveAll(personLanguageEntityList);

    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removeAccount(
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) AccountDto accountDto) {
        userPersonRepository.delete(UserPersonEntity.builder().userId(accountDto.getUserId()).build());
        userRepository.delete(UserEntity.builder().id(accountDto.getUserId()).build());
        personRepository.delete(PersonEntity.builder().id(accountDto.getPersonId()).build());
        roleUserRepository.delete(RoleUserEntity.builder().userId(accountDto.getUserId()).build());
        personSectorRepository.delete(PersonSectorEntity.builder().personId(accountDto.getPersonId()).build());
        personSkillRepository.delete(PersonSkillEntity.builder().personId(accountDto.getPersonId()).build());
        personLanguageRepository.delete(PersonLanguageEntity.builder().personId(accountDto.getPersonId()).build());
    }

}
