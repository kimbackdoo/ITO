package kr.co.metasoft.ito.api.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.app.dto.InvolvementDto;
import kr.co.metasoft.ito.api.app.service.InvolvementService;

@RestController
@RequestMapping (path = "api/app/involvements")
public class ApiAppInvolvementController {

    @Autowired
    InvolvementService involvementService;

    @PostMapping (path = "")
    public void createInvolvement(@RequestBody InvolvementDto involvementDto) {
        involvementService.createInvolvement(involvementDto);
    }

    @DeleteMapping (path = "")
    public void removeInvolvement(@RequestBody InvolvementDto involvementDto) {
        involvementService.removeInvolvement(involvementDto);
    }


}
