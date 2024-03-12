package universe.universe.domain.message.entity;

import jakarta.persistence.*;
import lombok.Getter;
import universe.universe.global.common.BaseEntity;
import universe.universe.domain.chatRoom.entity.ChatRoom;
import universe.universe.domain.user.entity.User;

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
    @JoinColumn(name = "user_id") // 발신자의 ID
    private User user;

    private String content;

    protected Message() {

    }

    public Message(User findUser, ChatRoom findChatRoom, String messageContent) {
        this.user = findUser;
        this.chatRoom = findChatRoom;
        this.content = messageContent;
    }
}
