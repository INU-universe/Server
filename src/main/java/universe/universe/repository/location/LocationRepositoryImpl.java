package universe.universe.repository.location;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import universe.universe.dto.location.LocationResponseDTO;
import universe.universe.entitiy.location.QLocation;
import universe.universe.entitiy.user.QUser;

public class LocationRepositoryImpl implements LocationRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public LocationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public LocationResponseDTO.LocationFindOneDTO findOne(Long userId) {
        QUser qUser = QUser.user;
        QLocation qLocation = QLocation.location;
        return queryFactory
                .select(Projections.constructor(LocationResponseDTO.LocationFindOneDTO.class, qLocation))
                .from(qUser)
                .leftJoin(qUser.location, qLocation)
                .where(qUser.id.eq(userId))
                .fetchOne();
    }
}
