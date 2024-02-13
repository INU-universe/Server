package universe.universe.dto.location;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.location.Location;

@Data
public class LocationResponseDTO {
    @Setter
    @Getter
    public static class LocationFindDTO {
        private Long id;
        private double latitude;
        private double longitude;

        public LocationFindDTO(Location location) {
            this.id = location.getId();
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
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
