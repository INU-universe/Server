package universe.universe.domain.friend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import universe.universe.global.common.BaseEntity;
import universe.universe.domain.user.entity.User;

@Entity
@Getter
public class Friend extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @Enumerated(EnumType.STRING)
    private FriendStatus friendStatus;

    /** ======================== 메소드 ======================== **/
    public void updateFriendStatus(FriendStatus friendStatus) {
        this.friendStatus = friendStatus;
    }

    /** ======================== 생성자 ======================== **/

    public Friend() {

    }

    public Friend(User fromUser, User toUser, FriendStatus friendStatus) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.friendStatus = friendStatus;
    }
}
