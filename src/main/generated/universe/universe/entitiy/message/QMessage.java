package universe.universe.entitiy.message;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import universe.universe.domain.message.entity.Message;


/**
 * QMessage is a Querydsl query type for Message
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessage extends EntityPathBase<Message> {

    private static final long serialVersionUID = -2106020872L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessage message = new QMessage("message");

    public final universe.universe.entitiy.base.QBaseEntity _super = new universe.universe.entitiy.base.QBaseEntity(this);

    public final universe.universe.entitiy.chatRoom.QChatRoom chatRoom;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final universe.universe.entitiy.user.QUser user;

    public QMessage(String variable) {
        this(Message.class, forVariable(variable), INITS);
    }

    public QMessage(Path<? extends Message> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessage(PathMetadata metadata, PathInits inits) {
        this(Message.class, metadata, inits);
    }

    public QMessage(Class<? extends Message> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new universe.universe.entitiy.chatRoom.QChatRoom(forProperty("chatRoom")) : null;
        this.user = inits.isInitialized("user") ? new universe.universe.entitiy.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

