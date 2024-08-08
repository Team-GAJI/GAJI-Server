package gaji.service.domain.room.repository;

import gaji.service.domain.room.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
