package universe.universe.dto.location;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.friend.Friend;
import universe.universe.entitiy.friend.FriendStatus;
import universe.universe.entitiy.location.Location;
import universe.universe.entitiy.user.UserStatus;

import java.util.List;

@Data
public class LocationResponseDTO {
    @Setter
    @Getter
    public static class LocationFindOneDTO {
        private Long locationId;
        private double latitude;
        private double longitude;
        private Long userId;
        private String userName;
        private String userImg;
        private UserStatus userStatus;

        public LocationFindOneDTO(Location location) {
            this.locationId = location.getId();
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
            this.userId = location.getUser().getId();
            this.userName = location.getUser().getUserName();
            this.userImg = location.getUser().getUserImg();
            this.userStatus = location.getUser().getUserStatus();
        }

        public LocationFindOneDTO() {
        }
        public LocationFindOneDTO(Long locationId, double latitude, double longitude, Long userId, String userName, String userImg, UserStatus userStatus) {
            this.locationId = locationId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.userId = userId;
            this.userName = userName;
            this.userImg = userImg;
            this.userStatus = userStatus;
        }
    }

    @Setter
    @Getter
    public static class LocationFindAllDTO {
        private LocationResponseDTO.LocationFindOneDTO userLocation;
        private List<LocationResponseDTO.LocationFindOneDTO> friendLocationList;

        public LocationFindAllDTO(LocationFindOneDTO userLocation, List<LocationFindOneDTO> friendLocationList) {
            this.userLocation = userLocation;
            this.friendLocationList = friendLocationList;
        }
    }

    @Setter
    @Getter
    public static class LocationUpdateDTO {
        private Long id;
        private double latitude;
        private double longitude;
        public LocationUpdateDTO(Location location) {
            this.id = location.getId();
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
        }
    }
}
