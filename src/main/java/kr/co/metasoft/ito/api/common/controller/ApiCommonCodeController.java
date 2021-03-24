package kr.co.metasoft.ito.api.common.controller;

import java.util.ArrayList;
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

import kr.co.metasoft.ito.api.common.dto.CodeDto;
import kr.co.metasoft.ito.api.common.dto.CodeParamDto;
import kr.co.metasoft.ito.api.common.entity.CodeEntity;
import kr.co.metasoft.ito.api.common.service.CodeService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@RestController
@RequestMapping (path = "api/common/codes")
public class ApiCommonCodeController {

    @Autowired
    private CodeService codeService;

    @GetMapping (path = "")
    public PageResponse<CodeEntity> getCodeList(
            @ModelAttribute CodeParamDto codeParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return codeService.getCodeList(codeParamDto, pageRequest);
    }

    @GetMapping (path = "{id}")
    public CodeEntity getCode(
            @PathVariable (name = "id") String id) {
        return codeService.getCode(id);
    }

    @PostMapping (path = "", params = {"bulk"})
    public List<CodeEntity> createCodeList(
            @RequestBody List<CodeDto> codeDtoList) {
        List<CodeEntity> codeList = new ArrayList<>();
        for (int i = 0; i < codeDtoList.size(); i++) {
            CodeDto codeDto = codeDtoList.get(i);
            CodeEntity codeEntity = CodeEntity.builder()
                    .parentId(codeDto.getParentId())
                    .name(codeDto.getName())
                    .ranking(codeDto.getRanking())
                    .status(codeDto.getStatus())
                    .build();
            codeList.add(codeEntity);
        }
        return codeService.createCodeList(codeList);
    }

    @PostMapping (path = "", params = {"!bulk"})
    public CodeEntity createCode(
            @RequestBody CodeDto codeDto) {
        CodeEntity codeEntity = CodeEntity.builder()
                .parentId(codeDto.getParentId())
                .name(codeDto.getName())
                .ranking(codeDto.getRanking())
                .status(codeDto.getStatus())
                .build();
        return codeService.createCode(codeEntity);
    }

    @PutMapping (path = "")
    public List<CodeEntity> modifyCodeList(
            @RequestBody List<CodeDto> codeDtoList) {
        List<CodeEntity> codeList = new ArrayList<>();
        for (int i = 0; i < codeDtoList.size(); i++) {
            CodeDto codeDto = codeDtoList.get(i);
            CodeEntity codeEntity = CodeEntity.builder()
                    .id(codeDto.getId())
                    .parentId(codeDto.getParentId())
                    .name(codeDto.getName())
                    .ranking(codeDto.getRanking())
                    .status(codeDto.getStatus())
                    .build();
            codeList.add(codeEntity);
        }
        return codeService.modifyCodeList(codeList);
    }

    @PutMapping (path = "{id}")
    public CodeEntity modifyCode(
            @PathVariable (name = "id") String id,
            @RequestBody CodeDto codeDto) {
        CodeEntity codeEntity = CodeEntity.builder()
                .id(id)
                .parentId(codeDto.getParentId())
                .name(codeDto.getName())
                .ranking(codeDto.getRanking())
                .status(codeDto.getStatus())
                .build();
        return codeService.modifyCode(codeEntity);
    }

    @DeleteMapping (path = "")
    public void removeCodeList(
            @RequestBody List<String> idList) {
        codeService.removeCodeList(idList);
    }

    @DeleteMapping (path = "{id}")
    public void removeCode(
            @PathVariable (name = "id") String id) {
        codeService.removeCode(id);
    }
}
