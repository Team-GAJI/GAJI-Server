package gaji.service.domain.recruit.repository;

import gaji.service.domain.recruit.entity.RecruitPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<RecruitPost, Long> {

}
