package universe.universe.repository.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import universe.universe.entitiy.token.RefreshToken;

public interface TokenRepository extends CrudRepository<RefreshToken, String> {

    boolean existsByRefreshToken(String token);
}
