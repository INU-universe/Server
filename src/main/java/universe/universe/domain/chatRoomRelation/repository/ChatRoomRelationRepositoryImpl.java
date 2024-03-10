package universe.universe.domain.chatRoomRelation.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import universe.universe.domain.chatRoom.dto.ChatRoomResponseDTO;
import universe.universe.entitiy.chatRoom.QChatRoomRelation;
import universe.universe.entitiy.user.QUser;

import java.util.List;

public class ChatRoomRelationRepositoryImpl implements ChatRoomRelationRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public ChatRoomRelationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public ChatRoomResponseDTO.ChatRoomFindAllDTO ChatRoomRelationFindAll(Long userId) {
        QChatRoomRelation chatRoomRelation = QChatRoomRelation.chatRoomRelation;
        QUser user = QUser.user;

        List<ChatRoomResponseDTO.ChatRoomFindOneDTO> chatRoomRelationList = queryFactory
                .select(Projections.constructor(ChatRoomResponseDTO.ChatRoomFindOneDTO.class,
                        chatRoomRelation.chatRoom.id,
                        Projections.list(Projections.constructor(ChatRoomResponseDTO.ChatRoomUserInfoDTO.class,
                                user.id,
                                user.userName,
                                user.userImg,
                                chatRoomRelation.id))))
                .from(chatRoomRelation)
                .join(chatRoomRelation.chatRoom)
                .join(chatRoomRelation.user, user)
                .where(chatRoomRelation.chatRoom.id.in(
                        JPAExpressions.select(chatRoomRelation.chatRoom.id)
                                .from(chatRoomRelation)
                                .where(chatRoomRelation.user.id.eq(userId))
                ))
                .where(chatRoomRelation.user.id.ne(userId))
                .fetch();

        return new ChatRoomResponseDTO.ChatRoomFindAllDTO(userId, chatRoomRelationList);
    }
}
