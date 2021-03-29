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

import kr.co.metasoft.ito.api.common.dto.PersonSectorDto;
import kr.co.metasoft.ito.api.common.dto.PersonSectorParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonSectorEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonSectorEntityId;
import kr.co.metasoft.ito.api.common.service.PersonSectorService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@RestController
@RequestMapping (path = "api/common/person-sectors")
public class ApiCommonPersonSectorController {

    @Autowired
    private PersonSectorService personSectorService;

    @GetMapping (path = "")
    public PageResponse<PersonSectorEntity> getPersonSectorList(
            @ModelAttribute PersonSectorParamDto personSectorParamDto,
            @ModelAttribute PageRequest pageRequest) {
        return personSectorService.getPersonSectorList(personSectorParamDto, pageRequest);
    }

    @GetMapping (path = "{personId},{sector}")
    public PersonSectorEntity getPersonSector(
            @PathVariable (name = "personId") Long personId,
            @PathVariable (name = "sector") String sector) {
        return personSectorService.getPersonSector(personId, sector);
    }

    @PostMapping (path = "", params = {"bulk"})
    public List<PersonSectorEntity> createPersonSectorList(
            @RequestBody List<PersonSectorDto> personSectorDtoList) {
        List<PersonSectorEntity> personSectorList = new ArrayList<>();
        for (int i = 0; i < personSectorDtoList.size(); i++) {
            PersonSectorDto personSectorDto = personSectorDtoList.get(i);
            PersonSectorEntity personSectorEntity = PersonSectorEntity.builder()
                    .personId(personSectorDto.getPersonId())
                    .sector(personSectorDto.getSector())
                    .build();
            personSectorList.add(personSectorEntity);
        }
        return personSectorService.createPersonSectorList(personSectorList);
    }

    @PostMapping (path = "", params = {"!bulk"})
    public PersonSectorEntity createPersonSector(
            @RequestBody PersonSectorDto personSectorDto) {
        PersonSectorEntity personSectorEntity = PersonSectorEntity.builder()
                .personId(personSectorDto.getPersonId())
                .sector(personSectorDto.getSector())
                .build();
        return personSectorService.createPersonSector(personSectorEntity);
    }

    @PutMapping (path = "")
    public List<PersonSectorEntity> modifyPersonSectorList(
            @RequestBody List<PersonSectorDto> personSectorDtoList) {
        List<PersonSectorEntity> personSectorList = new ArrayList<>();
        for (int i = 0; i < personSectorDtoList.size(); i++) {
            PersonSectorDto personSectorDto = personSectorDtoList.get(i);
            PersonSectorEntity personSectorEntity = PersonSectorEntity.builder()
                    .personId(personSectorDto.getPersonId())
                    .sector(personSectorDto.getSector())
                    .build();
            personSectorList.add(personSectorEntity);
        }
        return personSectorService.modifyPersonSectorList(personSectorList);
    }

    @PutMapping (path = "{personId},{sector}")
    public PersonSectorEntity modifyPersonSector(
            @PathVariable (name = "personId") Long personId,
            @PathVariable (name = "sector") String sector,
            @RequestBody PersonSectorDto personSectorDto) {
        PersonSectorEntity personSectorEntity = PersonSectorEntity.builder()
                .personId(personId)
                .sector(sector)
                .build();
        return personSectorService.modifyPersonSector(personSectorEntity);
    }

    @DeleteMapping (path = "")
    public void removePersonSectorList(
            @RequestBody List<PersonSectorEntityId> personSectorEntityIdList) {
        personSectorService.removePersonSectorList(personSectorEntityIdList);
    }

    @DeleteMapping (path = "{personId},{sector}")
    public void removePersonSector(
            @PathVariable (name = "personId") Long personId,
            @PathVariable (name = "sector") String sector) {
        personSectorService.removePersonSector(personId, sector);
    }

}