package universe.universe.entitiy.message;

import jakarta.persistence.*;
import lombok.Getter;
import universe.universe.entitiy.base.BaseEntity;
import universe.universe.entitiy.chatRoom.ChatRoom;
import universe.universe.entitiy.user.User;

@Entity
@Getter
public class Message extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suer_id") // 발신자의 ID
    private User user;

    private String content;
}
