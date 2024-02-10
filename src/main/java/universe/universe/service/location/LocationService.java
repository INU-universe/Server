package universe.universe.service.location;

import universe.universe.dto.location.LocationRequestDTO;
import universe.universe.dto.location.LocationResponseDTO;

public interface LocationService {
    // 위치 정보 변경
    LocationResponseDTO.LocationUpdateDTO update(LocationRequestDTO.LocationUpdateDTO locationUpdateDTO, String userEmail);

    // 위치 정보 조회
    LocationResponseDTO.LocationFindDTO find(String userEmail);
}
