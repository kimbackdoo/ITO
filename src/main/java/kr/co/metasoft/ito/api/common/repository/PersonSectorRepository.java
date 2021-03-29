package kr.co.metasoft.ito.api.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.entity.PersonSectorEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonSectorEntityId;

@Repository
public interface PersonSectorRepository extends JpaRepository<PersonSectorEntity, PersonSectorEntityId> {

}