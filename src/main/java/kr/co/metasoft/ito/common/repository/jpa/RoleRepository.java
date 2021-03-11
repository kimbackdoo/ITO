package kr.co.metasoft.ito.common.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.common.entity.jpa.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {

}