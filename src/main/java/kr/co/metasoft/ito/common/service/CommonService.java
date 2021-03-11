package kr.co.metasoft.ito.common.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import kr.co.metasoft.ito.common.dto.ErrorDto;
import kr.co.metasoft.ito.common.entity.keyvalue.TokenEntity;
import kr.co.metasoft.ito.common.repository.keyvalue.TokenRepository;

@Service
public class CommonService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public List<TokenEntity> getTokenList() {
        List<TokenEntity> tokenList = new ArrayList<>();
        tokenRepository.findAll().iterator().forEachRemaining(tokenList::add);
        return tokenList;
    }

    @Transactional
    public List<RequestMappingInfo> getRequestMappingList() {
        List<RequestMappingInfo> requestMappingList = new ArrayList<>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> requestMappingInfoSet = handlerMethodMap.keySet();
        Iterator<RequestMappingInfo> requestMappingInfoIterator = requestMappingInfoSet.iterator();
        while (requestMappingInfoIterator.hasNext()) {
            RequestMappingInfo requestMappingInfo = requestMappingInfoIterator.next();
            requestMappingList.add(requestMappingInfo);
        }
        return requestMappingList;
    }

    @Transactional
    public Map<String, Object> getEncodedPassword(String password) {
        Map<String, Object> map = new HashMap<>();
        String encodedPassword = passwordEncoder.encode(password);
        map.put("encodedPassword", encodedPassword);
        return map;
    }

    @Transactional
    public Object logout(String token, String uri) {
        TokenEntity tokenEntity = token != null
                ? tokenRepository.findById(token).orElse(null)
                : null;
        if (token != null && tokenEntity != null) {
            tokenRepository.deleteById(token);
        } else {
            String timestamp = LocalDateTime.now().toString();
            int status = 200;
            String error = "User Already logged out Error";
            String message = "User Already logged out";
            String code = "ERROR_USER_ALREADY_LOGGED_OUT";
            String path = uri;
            List<FieldError> fieldErrors = new ArrayList<>();
            ErrorDto errorDto = new ErrorDto();
            errorDto.setTimestamp(timestamp);
            errorDto.setStatus(status);
            errorDto.setError(error);
            errorDto.setMessage(message);
            errorDto.setCode(code);
            errorDto.setPath(path);
            errorDto.setFieldErrors(fieldErrors);
            return errorDto;
        }
        return new HashMap<>();
    }

}