package universe.universe.entitiy.friend;

import jakarta.persistence.*;
import universe.universe.entitiy.base.BaseEntity;
import universe.universe.entitiy.user.Status;

public class Friend extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "friend_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status friendStatus;
}
