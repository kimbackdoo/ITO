package kr.co.metasoft.ito.api.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import kr.co.metasoft.ito.api.common.dto.CodeParamDto;
import kr.co.metasoft.ito.api.common.entity.CodeEntity;
import kr.co.metasoft.ito.api.common.mapper.CodeMapper;
import kr.co.metasoft.ito.api.common.repository.CodeRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ReadValidationGroup;
import kr.co.metasoft.ito.common.validation.group.RemoveValidationGroup;

@Validated
@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private CodeMapper codeMapper;

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public PageResponse<CodeEntity> getCodeList(
            @Valid CodeParamDto codeParamDto,
            PageRequest pageRequest) {
        Integer codeListCount = codeMapper.selectCodeListCount(codeParamDto);
        List<CodeEntity> codeList = codeMapper.selectCodeList(codeParamDto, pageRequest);
        PageResponse<CodeEntity> pageResponse = new PageResponse<>(pageRequest, codeListCount);
        pageResponse.setItems(codeList);
        return pageResponse;
    }

    @Validated (value = {ReadValidationGroup.class})
    @Transactional (readOnly = true)
    public CodeEntity getCode(
            @Valid @NotNull (groups = {ReadValidationGroup.class}) String id) {
        return codeMapper.selectCode(id);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public List<CodeEntity> createCodeList(
            @Valid @NotEmpty (groups = {CreateValidationGroup.class}) List<@NotNull (groups = {CreateValidationGroup.class}) CodeEntity> codeList) {
        return codeRepository.saveAll(codeList);
    }

    @Validated (value = {CreateValidationGroup.class})
    @Transactional
    public CodeEntity createCode(
            @Valid @NotNull (groups = {CreateValidationGroup.class}) CodeEntity codeEntity) {
        return codeRepository.save(codeEntity);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public List<CodeEntity> modifyCodeList(
            @Valid @NotEmpty (groups = {ModifyValidationGroup.class}) List<@NotNull (groups = {ModifyValidationGroup.class}) CodeEntity> codeList) {
        return codeRepository.saveAll(codeList);
    }

    @Validated (value = {ModifyValidationGroup.class})
    @Transactional
    public CodeEntity modifyCode(
            @Valid @NotNull (groups = {ModifyValidationGroup.class}) CodeEntity codeEntity) {
        return codeRepository.save(codeEntity);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removeCodeList(
            @Valid @NotEmpty (groups = {RemoveValidationGroup.class}) List<@NotNull (groups = {RemoveValidationGroup.class}) String> idList) {
        List<CodeEntity> codeList = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            String id = idList.get(i);
            codeList.add(CodeEntity.builder().id(id).build());
        }
        codeRepository.deleteAll(codeList);
    }

    @Validated (value = {RemoveValidationGroup.class})
    @Transactional
    public void removeCode(
            @Valid @NotNull (groups = {RemoveValidationGroup.class}) String id) {
        codeRepository.delete(CodeEntity.builder().id(id).build());
    }

}