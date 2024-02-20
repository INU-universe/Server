package universe.universe.dto.location;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.location.Location;

@Data
public class LocationRequestDTO {
    @Setter
    @Getter
    public static class LocationFindOneDTO {
        private double latitude;
        private double longitude;
        public Location toEntity() {
            return new Location(this.latitude, this.longitude);
        }
    }

    @Setter
    @Getter
    public static class LocationUpdateDTO {
        private double latitude;
        private double longitude;
    }
}
