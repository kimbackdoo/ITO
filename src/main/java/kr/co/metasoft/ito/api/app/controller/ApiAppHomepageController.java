package kr.co.metasoft.ito.api.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.app.service.HomepageService;
import kr.co.metasoft.ito.api.common.dto.ContactDto;
import kr.co.metasoft.ito.api.common.dto.ContactParamDto;
import kr.co.metasoft.ito.api.common.dto.NoticeDto;
import kr.co.metasoft.ito.api.common.dto.NoticeParamDto;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@RestController
@RequestMapping (path = "api/app/homepages")
public class ApiAppHomepageController {

    @Autowired
    private HomepageService homepageService;

    @GetMapping (path = "/notices")
    public PageResponse<NoticeDto> getNoticeList(
            @ModelAttribute NoticeParamDto noticeParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return homepageService.getNoticeList(noticeParamDto, pageRequest);
    }

    @GetMapping (path = "/contacts")
    public PageResponse<ContactDto> getContactList(
            @ModelAttribute ContactParamDto contactParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return homepageService.getContactList(contactParamDto, pageRequest);
    }

//    @GetMapping (path = "notices/{id}")
//    public UserEntity getNotice(
//            @PathVariable (name = "id") Long id) {
//        return homepageService.getUser(id);
//    }
}
