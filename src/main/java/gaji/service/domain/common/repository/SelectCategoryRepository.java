package gaji.service.domain.common.repository;

import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.common.repository.SelectCategoryQueryDslRepository;
import gaji.service.domain.enums.PostTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectCategoryRepository extends JpaRepository<SelectCategory, Long>, SelectCategoryQueryDslRepository {

    SelectCategory findByEntityIdAndType(Long entityId, PostTypeEnum type);
}
