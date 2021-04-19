package kr.co.metasoft.ito.api.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import kr.co.metasoft.ito.api.common.dto.ContactDto;
import kr.co.metasoft.ito.api.common.dto.ContactParamDto;
import kr.co.metasoft.ito.api.common.dto.NoticeDto;
import kr.co.metasoft.ito.api.common.dto.NoticeParamDto;
import kr.co.metasoft.ito.api.common.repository.HomepageRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

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

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public NoticeDto getNotice(
            @Valid @NotNull (groups = {ReadValidationGroup.class}) Integer id) {
        return homepageRepository.selectNotice(id);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public Integer createNoticeList(
            @Valid @NotEmpty (groups = {CreateValidationGroup.class}) List<@NotNull (groups = {CreateValidationGroup.class}) NoticeDto> noticeList) {
        return homepageRepository.saveNoticeList(noticeList);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public Integer createNotice(
            @Valid @NotNull (groups = {CreateValidationGroup.class}) NoticeDto noticeDto) {
        return homepageRepository.saveNotice(noticeDto);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public Integer modifyNoticeList(
            @Valid @NotEmpty (groups = {ModifyValidationGroup.class}) List<@NotNull (groups = {ModifyValidationGroup.class}) NoticeDto> noticeList) {
        return homepageRepository.modifyNoticeList(noticeList);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public Integer modifyNotice(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) NoticeDto noticeDto) {
        return homepageRepository.modifiyNotice(noticeDto);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public Integer removeNoticeList(
            @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) Integer> idList) {
        List<NoticeDto> noticeList = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            Integer id = idList.get(i);
            noticeList.add(NoticeDto.builder().idx(id).build());
        }
        return homepageRepository.deleteNoticeList(noticeList);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public Integer removeNotice(
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) Integer id) {
        return homepageRepository.deleteNotice(NoticeDto.builder().idx(id).build());
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

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public ContactDto getContact(
            @Valid @NotNull (groups = {ReadValidationGroup.class}) Integer id) {
        return homepageRepository.selectContact(id);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public Integer createContactList(
            @Valid @NotEmpty (groups = {CreateValidationGroup.class}) List<@NotNull (groups = {CreateValidationGroup.class}) ContactDto> contactList) {
        return homepageRepository.saveContactList(contactList);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public Integer createContact(
            @Valid @NotNull (groups = {CreateValidationGroup.class}) ContactDto contactDto) {
        return homepageRepository.saveContact(contactDto);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public Integer modifyContactList(
            @Valid @NotEmpty (groups = {ModifyValidationGroup.class}) List<@NotNull (groups = {ModifyValidationGroup.class}) ContactDto> contactList) {
        return homepageRepository.modifyContactList(contactList);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public Integer modifyContact(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) ContactDto contactDto) {
        return homepageRepository.modifiyContact(contactDto);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public Integer removeContactList(
            @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) Integer> idList) {
        List<ContactDto> contactList = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            Integer id = idList.get(i);
            contactList.add(ContactDto.builder().idx(id).build());
        }
        return homepageRepository.deleteContactList(contactList);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public Integer removeContact(
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) Integer id) {
        return homepageRepository.deleteContact(ContactDto.builder().idx(id).build());
    }
}
