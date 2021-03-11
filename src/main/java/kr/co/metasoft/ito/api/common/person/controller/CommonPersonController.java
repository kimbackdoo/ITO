package kr.co.metasoft.ito.api.common.person.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.common.person.service.CommonPersonService;
import kr.co.metasoft.ito.common.entity.jpa.PersonEntity;

@RestController
@RequestMapping (path = "api/common/people")
public class CommonPersonController {

    @Autowired
    private CommonPersonService commonPersonService;

    @GetMapping (path = "")
    public Page<PersonEntity> getPersonList(
            @ModelAttribute PersonEntity personEntity,
            @PageableDefault Pageable pageable) {
        return commonPersonService.getPersonList(personEntity, pageable);
    }

    @GetMapping (path = "{id}")
    public PersonEntity getPerson(
            @PathVariable (name = "id") Long id) {
        return commonPersonService.getPerson(id);
    }

    @PostMapping (path = "")
    public PersonEntity createPerson(
            @RequestBody PersonEntity personEntity) {
        return commonPersonService.createPerson(personEntity);
    }

    @PutMapping (path = "{id}")
    public PersonEntity modifyPerson(
            @PathVariable (name = "id") Long id,
            @RequestBody PersonEntity personEntity) {
        personEntity.setId(id);
        return commonPersonService.modifyPerson(personEntity);
    }

    @DeleteMapping (path = "{id}")
    public void removePerson(
            @PathVariable (name = "id") Long id) {
        commonPersonService.removePerson(id);
    }

}