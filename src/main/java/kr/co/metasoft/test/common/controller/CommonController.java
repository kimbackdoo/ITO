package kr.co.metasoft.test.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.test.common.service.CommonService;

@RestController
@RequestMapping (path = "")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @PostMapping (path = "public/test")
    public ResponseEntity<Object> test() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping (path = "public/tokens")
    public ResponseEntity<Object> getTokenList() {
        return new ResponseEntity<>(commonService.getTokenList(), HttpStatus.OK);
    }

    @GetMapping (path = "public/request-mappings")
    public ResponseEntity<Object> getRequestMappingList() {
        return new ResponseEntity<>(commonService.getRequestMappingList(), HttpStatus.OK);
    }

    @GetMapping (path = "public/encoded-password")
    public ResponseEntity<Object> getEncodedPassword(
            @RequestParam (name = "password") String password) {
        return new ResponseEntity<>(commonService.getEncodedPassword(password), HttpStatus.OK);
    }

    @GetMapping (path = "api")
    public Map<String, Object> api() {
        return new HashMap<>();
    }

    @PostMapping (path = "api/logout")
    public ResponseEntity<Object> logout(
            @RequestHeader (name = "Authorization", required = false) String authorization,
            HttpServletRequest request) {
        String token = authorization != null && authorization.startsWith("Bearer ")
                ? authorization.replace("Bearer ", "") : null;
        String uri = request.getRequestURI();
        return new ResponseEntity<>(commonService.logout(token, uri), HttpStatus.OK);
    }

}