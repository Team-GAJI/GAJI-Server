package gaji.service.domain.roomBoard.repository;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTroublePostRepository extends JpaRepository<RoomTroublePost> {
}
