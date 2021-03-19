package kr.co.metasoft.ito.api.common.repository;

import kr.co.metasoft.ito.api.common.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
