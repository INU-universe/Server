package universe.universe.repository.friend;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import universe.universe.dto.friend.FriendRequestResponseDTO;

import java.util.List;

import static universe.universe.entitiy.friend.QFriendRequest.friendRequest;

public class FriendRequestRepositoryImpl implements FriendRequestRepositoryCustom {
//    private final JPAQueryFactory queryFactory;
//    public FriendRequestRepositoryImpl(EntityManager em) {
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//    @Override
//    public FriendRequestResponseDTO.FriendRequestFindAllDTO findAll(Long userId) {
//        List<FriendRequestResponseDTO.FriendRequestFindOneDTO> friendRequestList = queryFactory
//                .select(Projections.constructor(FriendRequestResponseDTO.FriendRequestFindOneDTO.class,
//                        friendRequest.fromUser.id,
//                        friendRequest.fromUser.userImg,
//                        friendRequest.fromUser.userName
//                ))
//                .from(friendRequest)
//                .where(friendRequest.toUser.id.eq(userId))
//                .fetch();
//
//        return new FriendRequestResponseDTO.FriendRequestFindAllDTO(userId, friendRequestList);
//    }
}
