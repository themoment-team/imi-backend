package team.themoment.imi.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.themoment.imi.domain.user.entity.User;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    boolean existsByStudentId(int studentId);
    Optional<User> findByEmail(String email);
    Optional<User> getUserByStudentId(int studentId);
}