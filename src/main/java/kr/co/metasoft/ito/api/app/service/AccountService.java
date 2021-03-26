package kr.co.metasoft.ito.api.app.service;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import kr.co.metasoft.ito.api.app.dto.AccountDto;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.entity.RoleUserEntity;
import kr.co.metasoft.ito.api.common.entity.UserEntity;
import kr.co.metasoft.ito.api.common.entity.UserPersonEntity;
import kr.co.metasoft.ito.api.common.repository.PersonRepository;
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
                .skill(accountDto.getPersonDto().getSkill())
                .language(accountDto.getPersonDto().getLanguage())
                .sector(accountDto.getPersonDto().getSector())
                .role(accountDto.getPersonDto().getRole())
                .career(accountDto.getPersonDto().getCareer())
                .pay(accountDto.getPersonDto().getPay())
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
        UserPersonEntity userPersonEntity = new UserPersonEntity(userId, personId, null, null);
        RoleUserEntity roleUserEntity = new RoleUserEntity(userId, accountDto.getRoleId(), null, null);

        roleUserRepository.save(roleUserEntity);
        userPersonRepository.save(userPersonEntity);
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
                .skill(accountDto.getPersonDto().getSkill())
                .language(accountDto.getPersonDto().getLanguage())
                .sector(accountDto.getPersonDto().getSector())
                .role(accountDto.getPersonDto().getRole())
                .career(accountDto.getPersonDto().getCareer())
                .pay(accountDto.getPersonDto().getPay())
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
        UserPersonEntity userPersonEntity = new UserPersonEntity(userId, personId, null, null);
        RoleUserEntity roleUserEntity = new RoleUserEntity(userId, accountDto.getRoleId(), null, null);
        roleUserRepository.save(roleUserEntity);
        userPersonRepository.save(userPersonEntity);
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
                .skill(accountDto.getPersonDto().getSkill())
                .language(accountDto.getPersonDto().getLanguage())
                .sector(accountDto.getPersonDto().getSector())
                .role(accountDto.getPersonDto().getRole())
                .career(accountDto.getPersonDto().getCareer())
                .pay(accountDto.getPersonDto().getPay())
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

        UserPersonEntity userPersonEntity = new UserPersonEntity();

        userPersonEntity.setUserId(userId);
        userPersonEntity.setPersonId(personId);

        RoleUserEntity roleUserEntity = new RoleUserEntity();

        roleUserEntity.setUserId(userId);
        roleUserEntity.setRoleId(Long.valueOf(2));

        roleUserRepository.save(roleUserEntity);
        userPersonRepository.save(userPersonEntity);

    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removeAccount(
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) AccountDto accountDto) {
        userPersonRepository.delete(UserPersonEntity.builder().userId(accountDto.getUserId()).build());
        userRepository.delete(UserEntity.builder().id(accountDto.getUserId()).build());
        personRepository.delete(PersonEntity.builder().id(accountDto.getPersonId()).build());
        roleUserRepository.delete(RoleUserEntity.builder().userId(accountDto.getUserId()).build());
    }

}
