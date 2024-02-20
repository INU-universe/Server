package universe.universe.service.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.common.exception.Exception404;
import universe.universe.common.exception.Exception500;
import universe.universe.dto.friend.FriendRequestResponseDTO;
import universe.universe.dto.location.LocationRequestDTO;
import universe.universe.dto.location.LocationResponseDTO;
import universe.universe.entitiy.location.Location;
import universe.universe.entitiy.user.User;
import universe.universe.repository.location.LocationRepository;
import universe.universe.repository.user.UserRepository;

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
            Location findLocation = getLocation(userEmail);
            findLocation.updateLocation(locationUpdateDTO.getLatitude(), locationUpdateDTO.getLongitude());

            return new LocationResponseDTO.LocationUpdateDTO(findLocation);
        } catch (Exception e){
            throw new Exception500("Location update fail : "+e.getMessage());
        }
    }

    @Override
    public LocationResponseDTO.LocationFindOneDTO findOne(String userEmail) {
        try {
            Long userId = getUser(userEmail).getId();
            LocationResponseDTO.LocationFindOneDTO findLocation = locationRepository.findOne(userId);

            return findLocation;
        } catch (Exception e){
            throw new Exception500("Location findOne fail : "+e.getMessage());
        }
    }

    @Override
    public LocationResponseDTO.LocationFindAllDTO findAll(String userEmail) {
        try {
            User findUser = getUser(userEmail);
            LocationResponseDTO.LocationFindAllDTO locationList = locationRepository.findAll(findUser.getId());
            return locationList;
        } catch (Exception e) {
            throw new Exception500("Location findAll fail : "+e.getMessage());
        }
    }

    private User getUser(String userEmail) {
        User findUser = userRepository.findByUserEmail(userEmail);
        if(findUser == null) {
            throw new Exception404("해당 유저를 찾을 수 없습니다. User: " + findUser);
        }
        return findUser;
    }

    private Location getLocation(String userEmail) {
        Location findLocation = getUser(userEmail).getLocation();
        return findLocation;
    }
}
