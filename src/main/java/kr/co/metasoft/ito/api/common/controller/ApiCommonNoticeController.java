package kr.co.metasoft.ito.api.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.common.dto.Notice2Dto;
import kr.co.metasoft.ito.api.common.dto.Notice2ParamDto;
import kr.co.metasoft.ito.api.common.entity.NoticeEntity;
import kr.co.metasoft.ito.api.common.service.NoticeService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@RestController
@RequestMapping(path = "api/app/notices")
public class ApiCommonNoticeController {


    @Autowired
    private NoticeService noticeService;

    @GetMapping(path = "")
    public PageResponse<NoticeEntity> getNoticeList(
            @ModelAttribute Notice2ParamDto notice2ParamDto,
            @ModelAttribute PageRequest pageRequest){
        return noticeService.selectNoticeList(notice2ParamDto, pageRequest);
    }

    @GetMapping(path = "{id}")
    public NoticeEntity getNotice(
            @PathVariable (name = "id") Long id) {
        return noticeService.selectNotice(id);
    }

    @PostMapping(path = "", params = "{!bulk}")
    public NoticeEntity createNotice(
            @RequestBody Notice2Dto notice2Dto) {
        NoticeEntity noticeEntity = NoticeEntity.builder()
                .id(notice2Dto.getId())
                .userId(notice2Dto.getUserId())
                .title(notice2Dto.getTitle())
                .contents(notice2Dto.getContents())
                .build();
        return noticeService.createNotice(noticeEntity);
    }

    @PostMapping(path = "{id}")
    public NoticeEntity createNotice(
            @PathVariable (name = "id") Long id,
            @RequestBody Notice2Dto notice2Dto) {
        NoticeEntity noticeEntity = NoticeEntity.builder()
                .id(id)
                .userId(notice2Dto.getUserId())
                .title(notice2Dto.getTitle())
                .contents(notice2Dto.getContents())
                .build();
        return noticeService.modifyNotice(noticeEntity);
    }

    @DeleteMapping(path ="{id}")
    public void removeNotice(
            @PathVariable (name = "id") Long id) {
        noticeService.removeNotice(id);
    }

    @DeleteMapping(path = "")
    public void removeNoticeList(
            @RequestBody List<Long> idList) {
        noticeService.removeNoticeList(idList);
    }

}
