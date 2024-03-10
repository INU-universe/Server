package universe.universe.domain.chatRoom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import universe.universe.global.common.BaseEntity;

@Entity
@Getter
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "chatroom_id")
    private Long id;
}
