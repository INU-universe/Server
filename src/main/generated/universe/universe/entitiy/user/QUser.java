package universe.universe.entitiy.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.entity.UserStatus;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 667347238L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final universe.universe.entitiy.base.QBaseEntity _super = new universe.universe.entitiy.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final universe.universe.entitiy.location.QLocation location;

    public final StringPath role = createString("role");

    public final DateTimePath<java.time.LocalDateTime> schoolDate = createDateTime("schoolDate", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userImg = createString("userImg");

    public final StringPath userName = createString("userName");

    public final StringPath userPassword = createString("userPassword");

    public final EnumPath<UserStatus> userStatus = createEnum("userStatus", UserStatus.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.location = inits.isInitialized("location") ? new universe.universe.entitiy.location.QLocation(forProperty("location"), inits.get("location")) : null;
    }

}

