package universe.universe.domain.message.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import universe.universe.domain.message.dto.MessageResponseDTO;

import java.util.List;

import static universe.universe.entitiy.message.QMessage.message;

public class MessageRepositoryImpl implements MessageRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public MessageRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public MessageResponseDTO.MessageFindAllDTO findAll(Long chatRoomId) {
        List<MessageResponseDTO.MessageFindOneDTO> messageList = queryFactory
                .select(Projections.constructor(MessageResponseDTO.MessageFindOneDTO.class,
                        message.id,
                        message.user.id,
                        message.content,
                        message.createdTime
                ))
                .from(message)
                .where(message.chatRoom.id.eq(chatRoomId))
                .fetch();

        return new MessageResponseDTO.MessageFindAllDTO(chatRoomId, messageList);
    }
}
