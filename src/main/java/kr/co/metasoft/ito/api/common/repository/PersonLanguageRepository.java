package kr.co.metasoft.ito.api.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.entity.PersonLanguageEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonLanguageEntityId;

@Repository
public interface PersonLanguageRepository extends JpaRepository<PersonLanguageEntity, PersonLanguageEntityId> {

}