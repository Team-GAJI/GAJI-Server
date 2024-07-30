package gaji.service.domain.room.repository;

import gaji.service.domain.room.entity.MyRepeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepeatRepository extends JpaRepository<MyRepeat, Long> {
}
