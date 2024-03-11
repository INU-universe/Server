package universe.universe.domain.location.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.exception.Exception400;
import universe.universe.global.exception.Exception404;
import universe.universe.global.exception.Exception500;
import universe.universe.domain.location.dto.LocationRequestDTO;
import universe.universe.domain.location.dto.LocationResponseDTO;
import universe.universe.domain.location.entity.Location;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.location.repository.LocationRepository;
import universe.universe.domain.user.repository.UserRepository;

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
            User findUser = getUser_Email(userEmail);
            LocationResponseDTO.LocationFindAllDTO result = locationRepository.favoriteFindAll(findUser.getId());
            return result;
        } catch (Exception e) {
            throw new Exception500("Location findAll fail : "+e.getMessage());
        }
    }

    private User getUser_Email(String userEmail) {
        User findUser = userRepository.findByUserEmail(userEmail);
        if(findUser == null) {
            throw new Exception400("userEmail", "해당 유저를 찾을 수 없습니다.");
        }
        return findUser;
    }

    private Location getLocation_Email(String userEmail) {
        Location findLocation = getUser_Email(userEmail).getLocation();
        if(findLocation == null) {
            throw new Exception400("location", "해당 위치를 찾을 수 없습니다.");
        }
        return findLocation;
    }
}
