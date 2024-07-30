package gaji.service.domain.curriculum.repository;

import gaji.service.domain.curriculum.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
}
