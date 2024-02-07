package universe.universe.entitiy.chatRoom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import universe.universe.entitiy.base.BaseEntity;

@Entity
@Getter
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "chatroom_id")
    private Long id;
}
