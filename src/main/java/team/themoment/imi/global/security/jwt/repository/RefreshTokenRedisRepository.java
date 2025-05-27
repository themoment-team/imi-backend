package team.themoment.imi.global.security.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.themoment.imi.global.security.jwt.entity.RefreshToken;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}