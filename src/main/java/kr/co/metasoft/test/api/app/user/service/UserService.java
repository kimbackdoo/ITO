package kr.co.metasoft.test.api.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import kr.co.metasoft.test.api.app.user.dto.UserPersonDto;
import kr.co.metasoft.test.common.entity.jpa.PersonEntity;
import kr.co.metasoft.test.common.entity.jpa.UserEntity;
import kr.co.metasoft.test.common.entity.jpa.UserPersonEntity;
import kr.co.metasoft.test.common.entity.keyvalue.TokenEntity;
import kr.co.metasoft.test.common.repository.jpa.PersonRepository;
import kr.co.metasoft.test.common.repository.jpa.UserPersonRepository;
import kr.co.metasoft.test.common.repository.jpa.UserRepository;
import kr.co.metasoft.test.common.repository.keyvalue.TokenRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserPersonRepository userPersonRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Transactional
    public UserEntity getUserByToken(String token) {
        TokenEntity tokenEntity = tokenRepository.findById(token).orElse(new TokenEntity());
        String username = tokenEntity.getUsername();
        return userRepository.findById(username).orElse(new UserEntity());
    }

    @Transactional
    public void signUp(UserPersonDto userPersonDto) throws Exception {
        UserEntity userEntity = userPersonDto.getUser();
        PersonEntity personEntity = userPersonDto.getPerson();
        String userId = userEntity.getId();
        String password = userEntity.getPassword();
        if (!StringUtils.isEmpty(userId) && !userRepository.findById(userId).isPresent()) {
            String encodedPassword = passwordEncoder.encode(password);
            userEntity.setPassword(encodedPassword);
            userRepository.save(userEntity);
            personRepository.save(personEntity);
            Long personId = personEntity.getId();
            UserPersonEntity userPersonEntity = new UserPersonEntity();
            userPersonEntity.setUserId(userId);
            userPersonEntity.setPersonId(personId);
            userPersonRepository.save(userPersonEntity);
        } else {
            throw new Exception();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(username).orElse(null);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        String id = userEntity.getId();
        String password = userEntity.getPassword();
        return new User(id, password, AuthorityUtils.NO_AUTHORITIES);
    }

}