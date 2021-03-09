package kr.co.metasoft.test.api.app.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.test.api.app.api.dto.ApiDto;
import kr.co.metasoft.test.api.app.api.service.ApiService;
import kr.co.metasoft.test.common.dto.PageRequestDto;
import kr.co.metasoft.test.common.dto.PageResponseDto;

@RestController
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping (path = "api/app/apis")
    public PageResponseDto<ApiDto> getApiList(
            @ModelAttribute PageRequestDto pageRequestDto) {
        return apiService.getApiList(pageRequestDto);
    }

}