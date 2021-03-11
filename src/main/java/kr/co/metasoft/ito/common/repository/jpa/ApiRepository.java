package kr.co.metasoft.ito.common.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.common.entity.jpa.ApiEntity;
import kr.co.metasoft.ito.common.entity.jpa.id.ApiId;

@Repository
public interface ApiRepository extends JpaRepository<ApiEntity, ApiId> {

}