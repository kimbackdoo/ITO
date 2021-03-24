package kr.co.metasoft.ito.api.common.repository;

import kr.co.metasoft.ito.api.common.entity.PersonCareerEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonCareerEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonCareerRepository extends JpaRepository<PersonCareerEntity, PersonCareerEntityId> {

}
