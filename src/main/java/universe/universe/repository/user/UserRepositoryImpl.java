package universe.universe.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import universe.universe.dto.user.UserResponseDTO;
import static universe.universe.entitiy.user.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom{
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
}
