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
        } catch (Exception e){
            throw new Exception500("Location update fail : "+e.getMessage());
        }
    }

    @Override
    public LocationResponseDTO.LocationFindOneDTO findOne(String userEmail) {
        try {
            log.info("[LocationServiceImpl] findOne");
            Long userId = getUser_Email(userEmail).getId();
            LocationResponseDTO.LocationFindOneDTO result = locationRepository.findOne(userId);

            return result;
        } catch (Exception e){
            throw new Exception500("Location findOne fail : "+e.getMessage());
        }
    }

    @Override
    public LocationResponseDTO.LocationFindAllDTO notFavoriteFindAll(String userEmail) {
        try {
            log.info("[LocationServiceImpl] notFavoriteFindAll");
            User findUser = getUser_Email(userEmail);
            LocationResponseDTO.LocationFindAllDTO result = locationRepository.notFavoriteFindAll(findUser.getId());
            return result;
        } catch (Exception e) {
            throw new Exception500("Location findAll fail : "+e.getMessage());
        }
    }

    @Override
    public LocationResponseDTO.LocationFindAllDTO favoriteFindAll(String userEmail) {
        try {
            log.info("[LocationServiceImpl] favoriteFindAll");
            User findUser = getUser_Email(userEmail);
            LocationResponseDTO.LocationFindAllDTO result = locationRepository.favoriteFindAll(findUser.getId());
            return result;
        } catch (Exception e) {
            throw new Exception500("Location findAll fail : "+e.getMessage());
        }
    }

    private User getUser_Email(String userEmail) throws Exception {
        Optional<User> findUser = userRepository.findByUserEmail(userEmail);
        if(findUser == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return findUser.get();
    }

    private Location getLocation_Email(String userEmail) throws Exception {
        Location findLocation = getUser_Email(userEmail).getLocation();
        if(findLocation == null) {
            throw new CustomException(ErrorCode.LOCATION_NOT_FOUND);
        }
        return findLocation;
    }
}
