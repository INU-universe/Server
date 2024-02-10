package universe.universe.repository.token;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.entitiy.token.RefreshToken;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsByRefreshToken(String token);
}
