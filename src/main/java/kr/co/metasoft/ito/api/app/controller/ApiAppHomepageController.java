package kr.co.metasoft.ito.api.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping (path = "/notices/{id}")
    public NoticeDto getNotice(
            @PathVariable (name = "id") Integer id) {
        return homepageService.getNotice(id);
    }

    @PostMapping (path = "/notices", params = {"bulk"})
    public Integer createNoticeList(
            @RequestBody List<NoticeDto> noticeDtoList) {
        return homepageService.createNoticeList(noticeDtoList);
    }

    @PostMapping (path = "/notices", params = {"!bulk"})
    public Integer createNotice(
            @RequestBody NoticeDto noticeDto) {
        return homepageService.createNotice(noticeDto);
    }

    @PutMapping (path = "/notices")
    public Integer modifyNoticeList(
            @RequestBody List<NoticeDto> noticeDtoList) {
        return homepageService.modifyNoticeList(noticeDtoList);
    }

    @PutMapping (path = "/notices/{id}")
    public Integer modifyNotice(
            @PathVariable (name = "id") Integer id,
            @RequestBody NoticeDto noticeDto) {
        return homepageService.modifyNotice(noticeDto);
    }

    @DeleteMapping (path = "/notices")
    public Integer removeNoticeList(
            @RequestBody List<Integer> idList) {
        return homepageService.removeNoticeList(idList);
    }

    @DeleteMapping (path = "/notices/{id}")
    public Integer removeNotice(
            @PathVariable (name = "id") Integer id) {
        return homepageService.removeNotice(id);
    }

    @GetMapping (path = "/contacts")
    public PageResponse<ContactDto> getContactList(
            @ModelAttribute ContactParamDto contactParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return homepageService.getContactList(contactParamDto, pageRequest);
    }

    @GetMapping (path = "/contacts/{id}")
    public ContactDto getContact(
            @PathVariable (name = "id") Integer id) {
        return homepageService.getContact(id);
    }

    @PostMapping (path = "/contacts", params = {"bulk"})
    public Integer createContactList(
            @RequestBody List<ContactDto> contactDtoList) {
        return homepageService.createContactList(contactDtoList);
    }

    @PostMapping (path = "/contacts", params = {"!bulk"})
    public Integer createContact(
            @RequestBody ContactDto contactDto) {
        return homepageService.createContact(contactDto);
    }

    @PutMapping (path = "/contacts")
    public Integer modifyContactList(
            @RequestBody List<ContactDto> contactDtoList) {
        return homepageService.modifyContactList(contactDtoList);
    }

    @PutMapping (path = "/contacts/{id}")
    public Integer modifyContact(
            @PathVariable (name = "id") Integer id,
            @RequestBody ContactDto contactDto) {
        return homepageService.modifyContact(contactDto);
    }

    @DeleteMapping (path = "/contacts")
    public Integer removeContactList(
            @RequestBody List<Integer> idList) {
        return homepageService.removeContactList(idList);
    }

    @DeleteMapping (path = "/contacts/{id}")
    public Integer removeContact(
            @PathVariable (name = "id") Integer id) {
        return homepageService.removeContact(id);
    }
}
