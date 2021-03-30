package kr.co.metasoft.ito.api.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.entity.ProjectPersonEntity;
import kr.co.metasoft.ito.api.common.entity.id.ProjectPersonEntityId;

@Repository
public interface ProjectPersonRepository extends JpaRepository<ProjectPersonEntity, ProjectPersonEntityId> {

}
