package kr.co.metasoft.ito.api.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.app.dto.InvolvementDto;
import kr.co.metasoft.ito.api.app.service.InvolvementService;
import kr.co.metasoft.ito.api.common.dto.ProjectCareerParamDto;
import kr.co.metasoft.ito.api.common.entity.CareerEntity;
import kr.co.metasoft.ito.api.common.entity.ProjectCareerEntity;
import kr.co.metasoft.ito.api.common.service.ProjectCareerService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;
import lombok.Getter;

@RestController
@RequestMapping (path = "api/app/involvements")
public class ApiAppInvolvementController {

    @Autowired
    InvolvementService involvementService;

    @Autowired
    ProjectCareerService projectCareerService;

    @PostMapping (path = "")
    public void createInvolvement(@RequestBody InvolvementDto involvementDto) {
        involvementService.createInvolvement(involvementDto);
    }

    @DeleteMapping (path = "")
    public void removeInvolvement(@RequestBody InvolvementDto involvementDto) {
        involvementService.removeInvolvement(involvementDto);
    }

    //getInvolvementProjecrId 가져오기  => data

    @GetMapping(path = "{projectId},{personId}")
    public List<Long> getCareerIdList(@PathVariable Long projectId, @PathVariable Long personId) {

        ProjectCareerParamDto projectCareerParamDto = new ProjectCareerParamDto(projectId, null, personId);
         return projectCareerService.selectCareerIdList(projectCareerParamDto);
    }


    //removeInvolvementList



}
