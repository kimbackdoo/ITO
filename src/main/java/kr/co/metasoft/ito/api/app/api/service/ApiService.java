package kr.co.metasoft.ito.api.app.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.metasoft.ito.api.app.api.dto.ApiDto;
import kr.co.metasoft.ito.api.app.api.mapper.ApiMapper;
import kr.co.metasoft.ito.common.dto.PageRequestDto;
import kr.co.metasoft.ito.common.dto.PageResponseDto;

@Service
public class ApiService {

    @Autowired
    private ApiMapper apiMapper;

    @Transactional
    public PageResponseDto<ApiDto> getApiList(PageRequestDto pageRequestDto) {
        Integer totalRows = apiMapper.selectApiListCount();
        List<ApiDto> apiList = apiMapper.selectApiList(pageRequestDto);
        PageResponseDto<ApiDto> pageResponseDto = new PageResponseDto<>(pageRequestDto, totalRows);
        pageResponseDto.setItems(apiList);
        return pageResponseDto;
    }

}