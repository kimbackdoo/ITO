package kr.co.metasoft.ito.common.repository.keyvalue;

import java.util.Optional;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import kr.co.metasoft.ito.common.entity.keyvalue.TokenEntity;

public interface TokenRepository extends KeyValueRepository<TokenEntity, String> {

    public Optional<TokenEntity> findOneByUsername(String username);

}