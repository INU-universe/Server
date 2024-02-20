package universe.universe.repository.location;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import universe.universe.dto.location.LocationResponseDTO;
import universe.universe.entitiy.friend.FriendStatus;
import universe.universe.entitiy.friend.QFriend;
import universe.universe.entitiy.location.QLocation;
import universe.universe.entitiy.user.QUser;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public LocationResponseDTO.LocationFindAllDTO notFavoriteFindAll(Long userId) {
        QUser qUser = QUser.user;
        QLocation qLocation = QLocation.location;
        QFriend qFriend = QFriend.friend;

        List<LocationResponseDTO.LocationFindOneDTO> locations = queryFactory
                .select(Projections.constructor(LocationResponseDTO.LocationFindOneDTO.class, qLocation))
                .from(qFriend)
                .leftJoin(qFriend.toUser, qUser)
                .leftJoin(qUser.location, qLocation)
                .where(qFriend.fromUser.id.eq(userId)
                        .or(qUser.id.eq(userId)))
                .fetch();

        Result result = getUserLocation(userId, locations);

        return new LocationResponseDTO.LocationFindAllDTO(result.userLocation(), result.friendLocations());
    }

    @Override
    public LocationResponseDTO.LocationFindAllDTO favoriteFindAll(Long userId) {
        QUser qUser = QUser.user;
        QLocation qLocation = QLocation.location;
        QFriend qFriend = QFriend.friend;

        List<LocationResponseDTO.LocationFindOneDTO> locations = queryFactory
                .select(Projections.constructor(LocationResponseDTO.LocationFindOneDTO.class, qLocation))
                .from(qFriend)
                .leftJoin(qFriend.toUser, qUser)
                .leftJoin(qUser.location, qLocation)
                .where(qFriend.fromUser.id.eq(userId)
                        .and(qFriend.friendStatus.eq(FriendStatus.FAVORITE))
                        .or(qUser.id.eq(userId)))
                .fetch();

        Result result = getUserLocation(userId, locations);

        return new LocationResponseDTO.LocationFindAllDTO(result.userLocation(), result.friendLocations());
    }

    private static Result getUserLocation(Long userId, List<LocationResponseDTO.LocationFindOneDTO> locations) {
        LocationResponseDTO.LocationFindOneDTO userLocation = null;
        List<LocationResponseDTO.LocationFindOneDTO> friendLocations = new ArrayList<>();
        for (LocationResponseDTO.LocationFindOneDTO location : locations) {
            if (location.getUserId() != null && location.getUserId().equals(userId)) {
                userLocation = location;
            } else {
                friendLocations.add(location);
            }
        }
        Result result = new Result(userLocation, friendLocations);
        return result;
    }

    private record Result(LocationResponseDTO.LocationFindOneDTO userLocation, List<LocationResponseDTO.LocationFindOneDTO> friendLocations) {
    }
}
