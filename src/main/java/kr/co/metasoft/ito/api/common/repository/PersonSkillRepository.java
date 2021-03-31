package kr.co.metasoft.ito.api.common.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.entity.PersonSkillEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonSkillEntityId;

@Repository
public interface PersonSkillRepository extends JpaRepository<PersonSkillEntity, PersonSkillEntityId> {

    @Query(value = "DELETE FROM tb_person_skill WHERE person_id=:personId", nativeQuery=true)
    void deleteSkillAllByPersonId(@Param("person_id") Long personId);
}