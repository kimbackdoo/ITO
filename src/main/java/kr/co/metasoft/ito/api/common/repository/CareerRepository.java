package kr.co.metasoft.ito.api.common.repository;

import kr.co.metasoft.ito.api.common.entity.CareerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends JpaRepository<CareerEntity, Long> {
}
