package kr.co.metasoft.ito.api.app.controller;

import kr.co.metasoft.ito.api.app.dto.ProfileDto;
import kr.co.metasoft.ito.api.app.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/app/profile")
public class ApiAppProfileController {
    @Autowired
    ProfileService personService;

    @PutMapping(path = "")
    public void modifyPerson(@RequestBody ProfileDto personDto) { personService.modifyPerson(personDto); }

//    @DeleteMapping(path = "")
//    public void removePerson(@RequestBody PersonDto personDto) { personService.removePerson(personDto); }
}
