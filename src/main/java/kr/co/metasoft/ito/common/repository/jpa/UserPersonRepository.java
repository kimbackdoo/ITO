package kr.co.metasoft.ito.common.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.common.entity.jpa.UserPersonEntity;

@Repository
public interface UserPersonRepository extends JpaRepository<UserPersonEntity, String> {

}