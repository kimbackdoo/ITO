package kr.co.metasoft.ito;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.ito.api.app.dto.AccountDto;
import kr.co.metasoft.ito.api.app.service.AccountService;
import kr.co.metasoft.ito.api.common.dto.CodeParamDto;
import kr.co.metasoft.ito.api.common.dto.UserParamDto;
import kr.co.metasoft.ito.api.common.entity.CodeEntity;
import kr.co.metasoft.ito.api.common.service.CodeService;
import kr.co.metasoft.ito.api.common.service.UserService;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@SpringBootApplication
@EnableAsync
public class ITOApplication extends SpringBootServletInitializer {

    public static void main(String[] args) { SpringApplication.run(ITOApplication.class, args); }
}

@RestController
@RequestMapping (path = "api")
class ApiController {

    @Autowired
    AccountService accountService;

    @Autowired
    CodeService codeService;

    @Autowired
    UserService userService;

    @PostMapping (path = "/sign-up")
    public void userSignUp(@RequestBody AccountDto accountDto) {
        accountService.userSignUp(accountDto);
    }

    @PostMapping (path = "/id-exists")
    public Integer userIDExsts(@RequestBody UserParamDto userParamDto) {
        return userService.getUserExists(userParamDto);
    }

    @PostMapping (path = "/codes/{type}")
    public PageResponse<CodeEntity> getCodeByJobList(
            @PathVariable (name = "type") String type) {
        String idStartLike = "";
        switch (type) {
            case "jobs":      idStartLike = "001"; break;
            case "languages": idStartLike = "002"; break;
            case "roles":     idStartLike = "003"; break;
            case "technics":  idStartLike = "004"; break;
            case "sectors":   idStartLike = "005"; break;
            default:          idStartLike = "-";
        }
        CodeParamDto codeParamDto = CodeParamDto.builder()
                .status("T")
                .idStartLike(idStartLike)
                .build();
        PageRequest pageRequest = new PageRequest();
        List<String> sort = new ArrayList<>();
        sort.add("id,asc");
        pageRequest.setSort(sort);
        pageRequest.setRowSize(10000000);
        return codeService.getCodeList(codeParamDto, pageRequest);
    }
}