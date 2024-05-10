package universe.universe.domain.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import universe.universe.domain.user.dto.UserResponseDTO;
import static universe.universe.domain.friend.entity.QFriend.friend;
import static universe.universe.domain.user.entity.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public UserResponseDTO.UserFindDTO findOne(String userEmail) {
        UserResponseDTO.UserFindDTO userFindDTO = queryFactory
                .select(Projections.constructor(UserResponseDTO.UserFindDTO.class,
                        user.id,
                        user.userEmail,
                        user.userName,
                        user.role,
                        user.userImg,
                        user.userStatus
                ))
                .from(user)
                .where(user.userEmail.eq(userEmail))
                .fetchOne();

        if (userFindDTO == null) {
            return null;
        }
        return userFindDTO;
    }

    @Override
    public void delete(Long userId) {
        queryFactory
                .delete(friend).where(friend.fromUser.id.eq(userId)).execute();
        queryFactory
                .delete(friend).where(friend.toUser.id.eq(userId)).execute();
        queryFactory
                .delete(user).where(user.id.eq(userId)).execute();
    }
}
