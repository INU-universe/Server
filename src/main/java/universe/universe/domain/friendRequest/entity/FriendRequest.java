package universe.universe.domain.friendRequest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import universe.universe.global.common.BaseEntity;
import universe.universe.domain.user.entity.User;

@Entity
@Getter
public class FriendRequest extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "friend_request_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    /** ======================== 메소드 ======================== **/


    /** ======================== 생성자 ======================== **/
    public FriendRequest() {

    }
    public FriendRequest(User fromUser, User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}

