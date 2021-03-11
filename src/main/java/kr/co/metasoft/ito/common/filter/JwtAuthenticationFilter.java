package kr.co.metasoft.ito.common.filter;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.metasoft.ito.common.dto.ErrorDto;
import kr.co.metasoft.ito.common.entity.keyvalue.TokenEntity;
import kr.co.metasoft.ito.common.repository.keyvalue.TokenRepository;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
                    throws AuthenticationException {
        Map<String, Object> map = null;
        Authentication authentication = null;
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        String username = null;
        String password = null;
        try {
            map = new ObjectMapper().readValue(request.getInputStream(), Map.class);
        } catch (IOException e) {
            map = null;
        }
        if (map != null) {
            username = (String) map.get("username");
            password = (String) map.get("password");
            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    AuthorityUtils.NO_AUTHORITIES);
            authentication = authenticationManager.authenticate(
                    usernamePasswordAuthenticationToken);
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication auth)
                    throws
                        IOException,
                        ServletException {
        ServletContext servletContext = request.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        TokenRepository tokenRepository = webApplicationContext.getBean(TokenRepository.class);
        long expirationTime = 86400000; // 1000(ms) * 60(s) * 60(m) * 24(h) * 1(d)
        long currentTimeMillis = System.currentTimeMillis();
        LocalDateTime timestamp = Instant.ofEpochMilli(currentTimeMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
        User user = ((User) auth.getPrincipal());
        String username = user.getUsername();
        String token = JWT.create()
                .withSubject("Application")
                .withIssuedAt(new Date(currentTimeMillis))
                .withExpiresAt(new Date(currentTimeMillis + expirationTime))
                .withNotBefore(new Date(currentTimeMillis))
                .withClaim("username", username)
                .sign(Algorithm.HMAC256("metasoft"));
        String authorization = "Bearer " + token;
        ObjectMapper objectMapper = new ObjectMapper();
        String objectResponse = null;
        TokenEntity tokenEntity = tokenRepository.findOneByUsername(username).orElse(null);
        if (tokenEntity != null) {
            tokenRepository.delete(tokenEntity);
        }
        tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setUsername(username);
        tokenEntity.setTimestamp(timestamp);
        tokenRepository.save(tokenEntity);
        Map<String, Object> map = new HashMap<>();
        map.put("Authorization", authorization);
        objectResponse = objectMapper.writeValueAsString(map);
        response.setHeader("Authorization", authorization);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        try (ServletOutputStream out = response.getOutputStream()) {
            out.print(objectResponse);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed)
                    throws
                        IOException,
                        ServletException {
        String timestamp = LocalDateTime.now().toString();
        int status = 200;
        String error = "Wrong User Info Error";
        String message = "Wrong User Info";
        String code = "ERROR_WRONG_USER_INFO";
        String path = request.getRequestURI();
        List<FieldError> fieldErrors = new ArrayList<>();
        ErrorDto errorDto = new ErrorDto();
        errorDto.setTimestamp(timestamp);
        errorDto.setStatus(status);
        errorDto.setError(error);
        errorDto.setMessage(message);
        errorDto.setCode(code);
        errorDto.setPath(path);
        errorDto.setFieldErrors(fieldErrors);
        ObjectMapper objectMapper = new ObjectMapper();
        String errorResponse = objectMapper.writeValueAsString(errorDto);
        response.setStatus(status);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        try (ServletOutputStream out = response.getOutputStream()) {
            out.print(errorResponse);
        }
    }

}