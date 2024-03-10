package universe.universe.entitiy.chatRoom;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import universe.universe.domain.chatRoomRelation.entity.ChatRoomRelation;


/**
 * QChatRoomRelation is a Querydsl query type for ChatRoomRelation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomRelation extends EntityPathBase<ChatRoomRelation> {

    private static final long serialVersionUID = 707131586L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoomRelation chatRoomRelation = new QChatRoomRelation("chatRoomRelation");

    public final QChatRoom chatRoom;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final universe.universe.entitiy.user.QUser user;

    public QChatRoomRelation(String variable) {
        this(ChatRoomRelation.class, forVariable(variable), INITS);
    }

    public QChatRoomRelation(Path<? extends ChatRoomRelation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoomRelation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoomRelation(PathMetadata metadata, PathInits inits) {
        this(ChatRoomRelation.class, metadata, inits);
    }

    public QChatRoomRelation(Class<? extends ChatRoomRelation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom")) : null;
        this.user = inits.isInitialized("user") ? new universe.universe.entitiy.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

