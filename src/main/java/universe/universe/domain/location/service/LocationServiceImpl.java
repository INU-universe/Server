package universe.universe.domain.location.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.common.exception.CustomException;
import universe.universe.global.common.exception.Exception500;
import universe.universe.domain.location.dto.LocationRequestDTO;
import universe.universe.domain.location.dto.LocationResponseDTO;
import universe.universe.domain.location.entity.Location;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.location.repository.LocationRepository;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.global.common.reponse.ErrorCode;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    final private LocationRepository locationRepository;
    final private UserRepository userRepository;
    @Override
    @Transactional
    public LocationResponseDTO.LocationUpdateDTO update(LocationRequestDTO.LocationUpdateDTO locationUpdateDTO, String userEmail) {
        try {
            log.info("[LocationServiceImpl] update");
            Location findLocation = getLocation_Email(userEmail);
            findLocation.updateLocation(locationUpdateDTO.getLatitude(), locationUpdateDTO.getLongitude());

            return new LocationResponseDTO.LocationUpdateDTO(findLocation);
        } catch (CustomException ce){
            log.info("[CustomException] LocationServiceImpl update");
            throw ce;
        } catch (Exception e){
            log.info("[Exception500] LocationServiceImpl update");
            throw new CustomException(ErrorCode.SERVER_ERROR, "LocationServiceImpl update : " + e.getMessage());
        }
    }

    @Override
    public LocationResponseDTO.LocationFindOneDTO findOne(String userEmail) {
        try {
            log.info("[LocationServiceImpl] findOne");
            Long userId = getUser("email", userEmail).getId();
            LocationResponseDTO.LocationFindOneDTO result = locationRepository.findOne(userId);

            return result;
        } catch (CustomException ce){
            log.info("[CustomException] LocationServiceImpl findOne");
            throw ce;
        } catch (Exception e){
            log.info("[Exception500] LocationServiceImpl findOne");
            throw new CustomException(ErrorCode.SERVER_ERROR, "LocationServiceImpl findOne : " + e.getMessage());
        }
    }

    @Override
    public LocationResponseDTO.LocationFindAllDTO notFavoriteFindAll(String userEmail) {
        try {
            log.info("[LocationServiceImpl] notFavoriteFindAll");
            User findUser = getUser("email", userEmail);
            LocationResponseDTO.LocationFindAllDTO result = locationRepository.notFavoriteFindAll(findUser.getId());
            return result;
        } catch (Exception e) {
            log.info("[Exception500] LocationServiceImpl notFavoriteFindAll");
            throw new CustomException(ErrorCode.SERVER_ERROR, "LocationServiceImpl notFavoriteFindAll : " + e.getMessage());
        }
    }

    @Override
    public LocationResponseDTO.LocationFindAllDTO favoriteFindAll(String userEmail) {
        try {
            log.info("[LocationServiceImpl] favoriteFindAll");
            User findUser = getUser("email", userEmail);
            LocationResponseDTO.LocationFindAllDTO result = locationRepository.favoriteFindAll(findUser.getId());
            return result;
        } catch (Exception e) {
            log.info("[Exception500] LocationServiceImpl favoriteFindAll");
            throw new CustomException(ErrorCode.SERVER_ERROR, "LocationServiceImpl favoriteFindAll : " + e.getMessage());
        }
    }

    private User getUser(String type, Object value) throws CustomException {
        Optional<User> findUser = null;
        if (type.equals("email")) {
            findUser = userRepository.findByUserEmail((String) value);
        } else if (type.equals("id")) {
            findUser = userRepository.findById((Long) value);
        }

        if (findUser == null || !findUser.isPresent()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return findUser.get();
    }

    private Location getLocation_Email(String userEmail) throws CustomException  {
        Location findLocation = getUser("email", userEmail).getLocation();
        if(findLocation == null) {
            throw new CustomException(ErrorCode.LOCATION_NOT_FOUND);
        }
        return findLocation;
    }
}
