package kr.co.metasoft.test.api.common.api.controller;

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

import kr.co.metasoft.test.api.common.api.service.CommonApiService;
import kr.co.metasoft.test.common.entity.jpa.ApiEntity;
import kr.co.metasoft.test.common.entity.jpa.id.ApiId;

@RestController
@RequestMapping (path = "api/common/apis")
public class CommonApiController {

    @Autowired
    private CommonApiService commonApiService;

    @GetMapping (path = "")
    public Page<ApiEntity> getApiList(
            @ModelAttribute ApiEntity apiEntity,
            @PageableDefault Pageable pageable) {
        return commonApiService.getApiList(apiEntity, pageable);
    }

    @GetMapping (path = "{url},{method}")
    public ApiEntity getApi(
            @PathVariable (name = "url") String url,
            @PathVariable (name = "method") String method) {
        ApiId apiId = new ApiId();
        apiId.setUrl(url);
        apiId.setMethod(method);
        return commonApiService.getApi(apiId);
    }

    @PostMapping (path = "")
    public ApiEntity createApi(
            @RequestBody ApiEntity apiEntity) {
        return commonApiService.createApi(apiEntity);
    }

    @PutMapping (path = "{url},{method}")
    public ApiEntity modifyApi(
            @PathVariable (name = "url") String url,
            @PathVariable (name = "method") String method,
            @RequestBody ApiEntity apiEntity) {
        ApiId apiId = new ApiId();
        apiId.setUrl(url);
        apiId.setMethod(method);
        apiEntity.setApiId(apiId);
        return commonApiService.modifyApi(apiEntity);
    }

    @DeleteMapping (path = "{url},{method}")
    public void removeApi(
            @PathVariable (name = "url") String url,
            @PathVariable (name = "method") String method) {
        ApiId apiId = new ApiId();
        apiId.setUrl(url);
        apiId.setMethod(method);
        commonApiService.removeApi(apiId);
    }

}