package universe.universe.domain.location.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.common.exception.CustomException;
import universe.universe.domain.location.dto.LocationRequestDTO;
import universe.universe.domain.location.dto.LocationResponseDTO;
import universe.universe.domain.location.entity.Location;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.location.repository.LocationRepository;
import universe.universe.global.common.reponse.ErrorCode;
import universe.universe.global.common.CommonMethod;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final CommonMethod commonMethod;
    @Override
    @Transactional
    public LocationResponseDTO.LocationUpdateDTO update(LocationRequestDTO.LocationUpdateDTO locationUpdateDTO, String userEmail) {
        try {
            log.info("[LocationServiceImpl] update");
            Location findLocation = commonMethod.getLocation_Email(userEmail);
            findLocation.updateLocation(locationUpdateDTO.getLatitude(), locationUpdateDTO.getLongitude());

            return new LocationResponseDTO.LocationUpdateDTO(findLocation);
        } catch (CustomException ce){
            log.info("[CustomException] LocationServiceImpl update");
            throw ce;
        } catch (Exception e){
            log.info("[Exception500] LocationServiceImpl update");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] LocationServiceImpl update : " + e.getMessage());
        }
    }

    @Override
    public LocationResponseDTO.LocationFindOneDTO findOne(String userEmail) {
        try {
            log.info("[LocationServiceImpl] findOne");
            Long userId = commonMethod.getUser("email", userEmail).getId();
            return locationRepository.findOne(userId);
        } catch (CustomException ce){
            log.info("[CustomException] LocationServiceImpl findOne");
            throw ce;
        } catch (Exception e){
            log.info("[Exception500] LocationServiceImpl findOne");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] LocationServiceImpl findOne : " + e.getMessage());
        }
    }

    @Override
    public LocationResponseDTO.LocationFindAllDTO notFavoriteFindAll(String userEmail) {
        try {
            log.info("[LocationServiceImpl] notFavoriteFindAll");
            User findUser = commonMethod.getUser("email", userEmail);
            return locationRepository.notFavoriteFindAll(findUser.getId());
        } catch (Exception e) {
            log.info("[Exception500] LocationServiceImpl notFavoriteFindAll");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] LocationServiceImpl notFavoriteFindAll : " + e.getMessage());
        }
    }

    @Override
    public LocationResponseDTO.LocationFindAllDTO favoriteFindAll(String userEmail) {
        try {
            log.info("[LocationServiceImpl] favoriteFindAll");
            User findUser = commonMethod.getUser("email", userEmail);
            return locationRepository.favoriteFindAll(findUser.getId());
        } catch (Exception e) {
            log.info("[Exception500] LocationServiceImpl favoriteFindAll");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] LocationServiceImpl favoriteFindAll : " + e.getMessage());
        }
    }
}
