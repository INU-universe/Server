package universe.universe.domain.location.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LocationRequestDTO {
    @Setter
    @Getter
    public static class LocationUpdateDTO {
        private double latitude;
        private double longitude;
    }
}
