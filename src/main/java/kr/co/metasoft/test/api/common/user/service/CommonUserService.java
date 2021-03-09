package kr.co.metasoft.test.api.common.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.metasoft.test.common.entity.jpa.UserEntity;
import kr.co.metasoft.test.common.repository.jpa.UserRepository;

@Service
public class CommonUserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Page<UserEntity> getUserList(
            UserEntity userEntity,
            Pageable pageable) {
        return userRepository.findAll(Example.of(userEntity), pageable);
    }

    @Transactional
    public UserEntity getUser(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Transactional
    public UserEntity modifyUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Transactional
    public void removeUser(String id) {
        userRepository.deleteById(id);
    }

}