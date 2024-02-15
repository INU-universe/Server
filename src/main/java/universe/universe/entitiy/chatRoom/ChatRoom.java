package universe.universe.entitiy.chatRoom;

import jakarta.persistence.*;
import lombok.Getter;
import universe.universe.entitiy.base.BaseEntity;
import universe.universe.entitiy.message.Message;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "chatroom_id")
    private Long id;
}
