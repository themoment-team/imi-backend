package team.themoment.imi.global.security.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.themoment.imi.global.security.jwt.entity.RefreshTokenRedisEntity;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshTokenRedisEntity, String> {
    Boolean existsByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}