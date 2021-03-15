package kr.co.metasoft.ito.api.app.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        UserEntity userEntity = new UserEntity(
                accountDto.getUserDto().getId(),
                accountDto.getUserDto().getUsername(),
                accountDto.getUserDto().getPassword(),
                accountDto.getUserDto().getEmail(),
                LocalDate.now(),
                accountDto.getUserDto().getStatus(),
                accountDto.getUserDto().getCreatedDate(),
                accountDto.getUserDto().getLastModifiedDate());

        PersonEntity personEntity = new PersonEntity(
                accountDto.getPersonDto().getId(),
                accountDto.getPersonDto().getName(),
                accountDto.getPersonDto().getCreatedDate(),
                accountDto.getPersonDto().getLastModifiedDate());

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
        UserEntity userEntity = new UserEntity(
                accountDto.getUserDto().getId(),
                accountDto.getUserDto().getUsername(),
                accountDto.getUserDto().getPassword(),
                accountDto.getUserDto().getEmail(),
                accountDto.getUserDto().getLastModifiedPasswordDate(),
                accountDto.getUserDto().getStatus(),
                accountDto.getUserDto().getCreatedDate(),
                accountDto.getUserDto().getLastModifiedDate());
        PersonEntity personEntity = new PersonEntity(
                accountDto.getPersonDto().getId(),
                accountDto.getPersonDto().getName(),
                accountDto.getPersonDto().getCreatedDate(),
                accountDto.getPersonDto().getLastModifiedDate());

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

        UserEntity userEntity = new UserEntity();

        userEntity.setId(accountDto.getUserDto().getId());
        userEntity.setUsername(accountDto.getUserDto().getUsername());
        userEntity.setPassword(accountDto.getUserDto().getPassword());
        userEntity.setEmail(accountDto.getUserDto().getEmail());
        userEntity.setLastModifiedPasswordDate(LocalDate.now());
        userEntity.setStatus("F");
        userEntity.setCreatedDate(LocalDateTime.now());
        userEntity.setLastModifiedDate(LocalDateTime.now());

        PersonEntity personEntity = new PersonEntity();

        personEntity.setId(accountDto.getPersonDto().getId());
        personEntity.setName(accountDto.getPersonDto().getName());
        personEntity.setCreatedDate(LocalDateTime.now());
        personEntity.setLastModifiedDate(LocalDateTime.now());

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
