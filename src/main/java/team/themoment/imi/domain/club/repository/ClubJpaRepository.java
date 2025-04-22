package team.themoment.imi.domain.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.themoment.imi.domain.club.entity.Club;

@Repository
public interface ClubJpaRepository extends JpaRepository<Club, Long> {
}