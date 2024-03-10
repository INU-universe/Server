package universe.universe.domain.token.repository;

import org.springframework.data.repository.CrudRepository;
import universe.universe.domain.token.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
