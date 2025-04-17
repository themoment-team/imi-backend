package team.themoment.imi.domain.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.themoment.imi.domain.profile.entity.Profile;

@Repository
public interface ProfileJpaRepository extends JpaRepository<Profile, Long> {
}