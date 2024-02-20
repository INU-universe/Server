package universe.universe.service.location;

import universe.universe.dto.location.LocationRequestDTO;
import universe.universe.dto.location.LocationResponseDTO;

public interface LocationService {
    // 위치 정보 변경
    LocationResponseDTO.LocationUpdateDTO update(LocationRequestDTO.LocationUpdateDTO locationUpdateDTO, String userEmail);

    // 단일 위치 정보 조회
    LocationResponseDTO.LocationFindOneDTO findOne(String userEmail);

    // 전체 위치 정보 조회 (친한 친구 x)
    LocationResponseDTO.LocationFindAllDTO notFavoriteFindAll(String userEmail);

    // 전체 위치 정보 조회 (친한 친구)
    LocationResponseDTO.LocationFindAllDTO favoriteFindAll(String userEmail);
}
