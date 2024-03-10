package universe.universe.domain.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.domain.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {
}
