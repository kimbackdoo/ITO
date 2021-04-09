package kr.co.metasoft.ito.api.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.entity.ProjectCareerEntity;
import kr.co.metasoft.ito.api.common.entity.id.ProjectCareerEntityId;

@Repository
public interface ProjectCareerRepository extends JpaRepository<ProjectCareerEntity, ProjectCareerEntityId> {

}
