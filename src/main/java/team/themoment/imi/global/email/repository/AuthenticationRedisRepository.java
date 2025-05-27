package team.themoment.imi.global.email.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.themoment.imi.global.email.entity.Authentication;

@Repository
public interface AuthenticationRedisRepository extends CrudRepository<Authentication, String> {
}