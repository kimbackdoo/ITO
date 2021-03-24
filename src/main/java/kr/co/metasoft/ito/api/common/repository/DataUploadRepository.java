package kr.co.metasoft.ito.api.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.entity.PersonEntity;

@Repository
public interface DataUploadRepository extends JpaRepository<PersonEntity, Long>{

}

