package universe.universe.repository.token;

import org.springframework.data.repository.CrudRepository;
import universe.universe.entitiy.token.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
