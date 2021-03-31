package kr.co.metasoft.ito.api.common.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.entity.PersonSectorEntity;
import kr.co.metasoft.ito.api.common.entity.id.PersonSectorEntityId;

@Repository
public interface PersonSectorRepository extends JpaRepository<PersonSectorEntity, PersonSectorEntityId> {
    @Query(value = "DELETE FROM tb_person_sector WHERE person_id=:personId", nativeQuery=true)
    void deleteSectorAllByPersonId(@Param("person_id") Long personId);
}