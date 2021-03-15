package kr.co.metasoft.ito.api.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.entity.RoleMenuEntity;
import kr.co.metasoft.ito.api.common.entity.id.RoleMenuEntityId;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenuEntity, RoleMenuEntityId> {

}