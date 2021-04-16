package kr.co.metasoft.ito.api.app.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.metasoft.ito.api.common.dto.ContactDto;
import kr.co.metasoft.ito.api.common.dto.ContactParamDto;
import kr.co.metasoft.ito.api.common.dto.NoticeDto;
import kr.co.metasoft.ito.api.common.dto.NoticeParamDto;
import kr.co.metasoft.ito.api.common.repository.HomepageRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@Service
public class HomepageService {

    @Autowired
    HomepageRepository homepageRepository;

    @Transactional
    public PageResponse<NoticeDto> getNoticeList(
            @Valid NoticeParamDto noticeParamDto,
            PageRequest pageRequest) {
        Integer noticeListCount = homepageRepository.selectNoticeListCount(noticeParamDto);
        List<NoticeDto> noticeList = homepageRepository.selectNoticeList(noticeParamDto, pageRequest);
        PageResponse<NoticeDto> pageResponse = new PageResponse<>(pageRequest, noticeListCount);
        pageResponse.setItems(noticeList);

        return pageResponse;
    }

    @Transactional
    public PageResponse<ContactDto> getContactList(
            @Valid ContactParamDto contactParamDto,
            PageRequest pageRequest) {
        Integer contactListCount = homepageRepository.selectContactListCount(contactParamDto);
        List<ContactDto> contactList = homepageRepository.selectContactList(contactParamDto, pageRequest);
        PageResponse<ContactDto> pageResponse = new PageResponse<>(pageRequest, contactListCount);
        pageResponse.setItems(contactList);

        return pageResponse;
    }
}
