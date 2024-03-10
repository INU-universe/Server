package universe.universe.domain.friendRequest.repository;

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
