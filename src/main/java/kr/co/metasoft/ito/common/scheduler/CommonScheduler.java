package kr.co.metasoft.ito.common.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.metasoft.ito.api.app.user.service.UserService;
import kr.co.metasoft.ito.common.entity.jpa.UserEntity;
import kr.co.metasoft.ito.common.entity.keyvalue.TokenEntity;
import kr.co.metasoft.ito.common.repository.keyvalue.TokenRepository;

@Component
public class CommonScheduler {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled (cron = "0 * * * * ?")
    public void invalidateToken() {
        Iterator<TokenEntity> tokenIterator = tokenRepository.findAll().iterator();
        while (tokenIterator.hasNext()) {
            TokenEntity tokenEntity = tokenIterator.next();
            LocalDateTime timestamp = tokenEntity.getTimestamp();
            LocalDateTime now = LocalDateTime.now();
            timestamp = timestamp.plus(30, ChronoUnit.MINUTES);
            if (timestamp.isBefore(now)) {
                UserEntity userEntity = userService.getUserByToken(tokenEntity.getToken());
                simpMessagingTemplate.convertAndSendToUser(userEntity.getId(), "/logout", new HashMap<>());
                tokenRepository.delete(tokenEntity);
            }
        }
    }

}