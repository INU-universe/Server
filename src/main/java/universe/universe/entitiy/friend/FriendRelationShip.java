package universe.universe.entitiy.friend;

import jakarta.persistence.*;
import lombok.Getter;
import universe.universe.entitiy.user.User;
@Entity
@Getter
public class FriendRelationShip {
    @Id
    @GeneratedValue
    @Column(name = "friend_relationship_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

}
