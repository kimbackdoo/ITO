package kr.co.metasoft.test.common.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.metasoft.test.common.dto.ErrorDto;
import kr.co.metasoft.test.common.entity.keyvalue.TokenEntity;
import kr.co.metasoft.test.common.repository.keyvalue.TokenRepository;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
                    throws
                        IOException,
                        ServletException {
        ServletContext servletContext = request.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        TokenRepository tokenRepository = webApplicationContext.getBean(TokenRepository.class);
        String authorization = request.getHeader("Authorization");
        String token = authorization != null && authorization.startsWith("Bearer ")
                ? authorization.replace("Bearer ", "") : null;
        if (token != null) {
            try {
                TokenEntity tokenEntity = tokenRepository.findById(token).orElse(null);
                DecodedJWT decodedJwt = JWT
                        .require(Algorithm.HMAC256("metasoft"))
                        .build()
                        .verify(token);
                if (tokenEntity == null) {
                    throw new TokenExpiredException(null);
                }
                tokenEntity.setTimestamp(LocalDateTime.now());
                tokenRepository.save(tokenEntity);
                String username = decodedJwt.getClaim("username").asString();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        AuthorityUtils.NO_AUTHORITIES);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                chain.doFilter(request, response);
            } catch (AlgorithmMismatchException e) {
                String timestamp = LocalDateTime.now().toString();
                int status = 200;
                String error = "Algorithm Mismatch Error";
                String message = "Algorithm Mismatch";
                String code = "ERROR_ALGORITHM_MISMATCH";
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
            } catch (SignatureVerificationException e) {
                String timestamp = LocalDateTime.now().toString();
                int status = 200;
                String error = "Signature Verification Error";
                String message = "Signature Verification";
                String code = "ERROR_SIGNATURE_VERIFICATION";
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
            } catch (TokenExpiredException e) {
                String timestamp = LocalDateTime.now().toString();
                int status = 200;
                String error = "Token Expired Error";
                String message = "Token Expired";
                String code = "ERROR_TOKEN_EXPIRED";
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
            } catch (InvalidClaimException e) {
                String timestamp = LocalDateTime.now().toString();
                int status = 200;
                String error = "Invalid Claim Error";
                String message = "Invalid Claim";
                String code = "ERROR_INVALID_CLAIM";
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
            } catch (JWTVerificationException e) {
                String timestamp = LocalDateTime.now().toString();
                int status = 200;
                String error = "JWT Verification Error";
                String message = "JWT Verification";
                String code = "ERROR_JWT_VERIFICATION";
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
        } else {
            chain.doFilter(request, response);
        }
    }

}