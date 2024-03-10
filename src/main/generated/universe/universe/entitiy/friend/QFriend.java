package universe.universe.entitiy.friend;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import universe.universe.domain.friend.entity.Friend;
import universe.universe.domain.friend.entity.FriendStatus;


/**
 * QFriend is a Querydsl query type for Friend
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFriend extends EntityPathBase<Friend> {

    private static final long serialVersionUID = 1162173702L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFriend friend = new QFriend("friend");

    public final universe.universe.entitiy.base.QBaseEntity _super = new universe.universe.entitiy.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final EnumPath<FriendStatus> friendStatus = createEnum("friendStatus", FriendStatus.class);

    public final universe.universe.entitiy.user.QUser fromUser;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final universe.universe.entitiy.user.QUser toUser;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public QFriend(String variable) {
        this(Friend.class, forVariable(variable), INITS);
    }

    public QFriend(Path<? extends Friend> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFriend(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFriend(PathMetadata metadata, PathInits inits) {
        this(Friend.class, metadata, inits);
    }

    public QFriend(Class<? extends Friend> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fromUser = inits.isInitialized("fromUser") ? new universe.universe.entitiy.user.QUser(forProperty("fromUser"), inits.get("fromUser")) : null;
        this.toUser = inits.isInitialized("toUser") ? new universe.universe.entitiy.user.QUser(forProperty("toUser"), inits.get("toUser")) : null;
    }

}

