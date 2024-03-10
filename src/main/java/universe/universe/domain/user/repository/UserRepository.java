package universe.universe.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);
}
