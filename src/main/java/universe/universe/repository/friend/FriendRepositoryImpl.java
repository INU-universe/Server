package universe.universe.repository.friend;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import universe.universe.dto.friend.FriendResponseDTO;
import static universe.universe.entitiy.friend.QFriend.friend;
import static universe.universe.entitiy.user.QUser.user;

import java.util.List;

public class FriendRepositoryImpl implements FriendRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public FriendRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public FriendResponseDTO.FriendFindAllDTO friendFindAll(Long userId) {
        List<FriendResponseDTO.FriendFindOneDTO> friendList = queryFactory
                .select(Projections.constructor(FriendResponseDTO.FriendFindOneDTO.class, friend.toUser))
                .from(friend)
                .join(friend.toUser, user)
                .where(friend.fromUser.id.eq(userId))
                .fetch();

        return new FriendResponseDTO.FriendFindAllDTO(userId, friendList);
    }
}
