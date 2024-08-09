package gaji.service.domain.common.repository;

import gaji.service.domain.common.entity.Category;
import gaji.service.domain.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategory(CategoryEnum category);
    boolean existsByCategory(CategoryEnum category);
    boolean existsById(Long id);
}
