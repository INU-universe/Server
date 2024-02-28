package universe.universe.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.entitiy.message.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
