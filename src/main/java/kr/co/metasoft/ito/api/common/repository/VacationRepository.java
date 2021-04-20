package kr.co.metasoft.ito.api.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.entity.VacationEntity;

@Repository
public interface VacationRepository extends JpaRepository<VacationEntity, Long>{

}
