package kr.co.metasoft.test.api.common.api.service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.metasoft.test.common.entity.jpa.ApiEntity;
import kr.co.metasoft.test.common.entity.jpa.id.ApiId;
import kr.co.metasoft.test.common.repository.jpa.ApiRepository;

@Service
public class CommonApiService {

    @Autowired
    private ApiRepository apiRepository;

    @Transactional
    public Page<ApiEntity> getApiList(ApiEntity apiEntity, Pageable pageable) {
        return apiRepository.findAll(Example.of(apiEntity), pageable);
    }

    @Transactional
    public ApiEntity getApi(ApiId apiId) {
        return apiRepository.findById(apiId).orElse(null);
    }

    @Transactional
    public ApiEntity createApi(ApiEntity apiEntity) {
        return apiRepository.save(apiEntity);
    }

    @Transactional
    public ApiEntity modifyApi(ApiEntity apiEntity) {
        return apiRepository.save(apiEntity);
    }

    @Transactional
    public void removeApi(ApiId apiId) {
        apiRepository.deleteById(apiId);
    }

}