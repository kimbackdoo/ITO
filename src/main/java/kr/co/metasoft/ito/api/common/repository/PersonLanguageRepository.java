package kr.co.metasoft.ito.api.common.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.entity.PersonLanguageEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonLanguageEntityId;

@Repository
public interface PersonLanguageRepository extends JpaRepository<PersonLanguageEntity, PersonLanguageEntityId> {
    @Query(value = "DELETE FROM tb_person_language WHERE person_id=:personId", nativeQuery=true)
    void deleteLanguageAllByPersonId(@Param("person_id") Long personId);
}