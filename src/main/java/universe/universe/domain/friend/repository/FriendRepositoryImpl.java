package universe.universe.domain.friend.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import universe.universe.domain.friend.dto.FriendResponseDTO;
import universe.universe.domain.user.entity.UserStatus;
import static universe.universe.domain.friend.entity.QFriend.friend;

import java.util.List;

public class FriendRepositoryImpl implements FriendRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public FriendRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public FriendResponseDTO.FriendFindAllDTO findAll(Long userId) {
        List<FriendResponseDTO.FriendFindOneDTO> friendList = queryFactory
                .select(Projections.constructor(FriendResponseDTO.FriendFindOneDTO.class,
                        friend.id,
                        friend.toUser.id,
                        friend.toUser.userImg,
                        friend.toUser.userName,
                        friend.friendStatus,
                        friend.toUser.userStatus,
                        friend.toUser.schoolDate
                ))
                .from(friend)
                .where(friend.fromUser.id.eq(userId))
                .fetch();

        return new FriendResponseDTO.FriendFindAllDTO(userId, friendList);
    }

    @Override
    public FriendResponseDTO.FriendFindInSchoolDTO findInSchool(Long userId) {
        List<FriendResponseDTO.FriendFindOneDTO> friendList = queryFactory
                .select(Projections.constructor(FriendResponseDTO.FriendFindOneDTO.class,
                        friend.id,
                        friend.toUser.id,
                        friend.toUser.userImg,
                        friend.toUser.userName,
                        friend.friendStatus,
                        friend.toUser.userStatus,
                        friend.toUser.schoolDate
                ))
                .from(friend)
                .where(friend.fromUser.id.eq(userId).and(friend.toUser.userStatus.eq(UserStatus.SCHOOL)))
                .fetch();

        return new FriendResponseDTO.FriendFindInSchoolDTO(userId, friendList);
    }
}
