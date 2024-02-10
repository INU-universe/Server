package universe.universe.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.entitiy.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);
}
