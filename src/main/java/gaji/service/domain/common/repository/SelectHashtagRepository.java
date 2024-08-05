package gaji.service.domain.common.repository;

import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.enums.PostTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectHashtagRepository extends JpaRepository<SelectHashtag, Long>, SelectHashtagCustomRepository {

    void deleteAllByEntityIdAndType(Long entityId, PostTypeEnum type);
}
