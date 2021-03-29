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

import kr.co.metasoft.ito.api.common.dto.PersonLanguageDto;
import kr.co.metasoft.ito.api.common.dto.PersonLanguageParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonLanguageEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonLanguageEntityId;
import kr.co.metasoft.ito.api.common.service.PersonLanguageService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@RestController
@RequestMapping (path = "api/common/person-languages")
public class ApiCommonPersonLanguageController {

    @Autowired
    private PersonLanguageService personLanguageService;

    @GetMapping (path = "")
    public PageResponse<PersonLanguageEntity> getPersonLanguageList(
            @ModelAttribute PersonLanguageParamDto personLanguageParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return personLanguageService.getPersonLanguageList(personLanguageParamDto, pageRequest);
    }

    @GetMapping (path = "{personId},{language}")
    public PersonLanguageEntity getPersonLanguage(
            @PathVariable (name = "personId") Long personId,
            @PathVariable (name = "language") String language) {
        return personLanguageService.getPersonLanguage(personId, language);
    }

    @PostMapping (path = "", params = {"bulk"})
    public List<PersonLanguageEntity> createPersonLanguageList(
            @RequestBody List<PersonLanguageDto> personLanguageDtoList) {
        List<PersonLanguageEntity> personLanguageList = new ArrayList<>();
        for (int i = 0; i < personLanguageDtoList.size(); i++) {
            PersonLanguageDto personLanguageDto = personLanguageDtoList.get(i);
            PersonLanguageEntity personLanguageEntity = PersonLanguageEntity.builder()
                    .personId(personLanguageDto.getPersonId())
                    .language(personLanguageDto.getLanguage())
                    .build();
            personLanguageList.add(personLanguageEntity);
        }
        return personLanguageService.createPersonLanguageList(personLanguageList);
    }

    @PostMapping (path = "", params = {"!bulk"})
    public PersonLanguageEntity createPersonLanguage(
            @RequestBody PersonLanguageDto personLanguageDto) {
        PersonLanguageEntity personLanguageEntity = PersonLanguageEntity.builder()
                .personId(personLanguageDto.getPersonId())
                .language(personLanguageDto.getLanguage())
                .build();
        return personLanguageService.createPersonLanguage(personLanguageEntity);
    }

    @PutMapping (path = "")
    public List<PersonLanguageEntity> modifyPersonLanguageList(
            @RequestBody List<PersonLanguageDto> personLanguageDtoList) {
        List<PersonLanguageEntity> personLanguageList = new ArrayList<>();
        for (int i = 0; i < personLanguageDtoList.size(); i++) {
            PersonLanguageDto personLanguageDto = personLanguageDtoList.get(i);
            PersonLanguageEntity personLanguageEntity = PersonLanguageEntity.builder()
                    .personId(personLanguageDto.getPersonId())
                    .language(personLanguageDto.getLanguage())
                    .build();
            personLanguageList.add(personLanguageEntity);
        }
        return personLanguageService.modifyPersonLanguageList(personLanguageList);
    }

    @PutMapping (path = "{personId},{language}")
    public PersonLanguageEntity modifyPersonLanguage(
            @PathVariable (name = "personId") Long personId,
            @PathVariable (name = "language") String language,
            @RequestBody PersonLanguageDto personLanguageDto) {
        PersonLanguageEntity personLanguageEntity = PersonLanguageEntity.builder()
                .personId(personId)
                .language(language)
                .build();
        return personLanguageService.modifyPersonLanguage(personLanguageEntity);
    }

    @DeleteMapping (path = "")
    public void removePersonLanguageList(
            @RequestBody List<PersonLanguageEntityId> personLanguageEntityIdList) {
        personLanguageService.removePersonLanguageList(personLanguageEntityIdList);
    }

    @DeleteMapping (path = "{personId},{language}")
    public void removePersonLanguage(
            @PathVariable (name = "personId") Long personId,
            @PathVariable (name = "language") String language) {
        personLanguageService.removePersonLanguage(personId, language);
    }

}