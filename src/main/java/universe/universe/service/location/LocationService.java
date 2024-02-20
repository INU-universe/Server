package universe.universe.service.location;

import universe.universe.dto.location.LocationRequestDTO;
import universe.universe.dto.location.LocationResponseDTO;

public interface LocationService {
    // 위치 정보 변경
    LocationResponseDTO.LocationUpdateDTO update(LocationRequestDTO.LocationUpdateDTO locationUpdateDTO, String userEmail);

    // 단일 위치 정보 조회
    LocationResponseDTO.LocationFindOneDTO findOne(String userEmail);

    // 전체 위치 정보 조회 (현재 회원 및 친구)
    LocationResponseDTO.LocationFindAllDTO findAll(String userEmail);
}
