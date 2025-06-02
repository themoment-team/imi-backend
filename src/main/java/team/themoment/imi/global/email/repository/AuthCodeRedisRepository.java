package team.themoment.imi.global.email.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.themoment.imi.global.email.entity.AuthCode;

import java.util.Optional;

@Repository
public interface AuthCodeRedisRepository extends CrudRepository<AuthCode, String> {
}