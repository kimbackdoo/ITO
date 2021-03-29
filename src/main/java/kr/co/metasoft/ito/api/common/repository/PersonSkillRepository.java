package kr.co.metasoft.ito.api.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.entity.PersonSkillEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonSkillEntityId;

@Repository
public interface PersonSkillRepository extends JpaRepository<PersonSkillEntity, PersonSkillEntityId> {

}