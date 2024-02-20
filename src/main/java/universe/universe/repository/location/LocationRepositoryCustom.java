package universe.universe.repository.location;

import universe.universe.dto.location.LocationResponseDTO;

public interface LocationRepositoryCustom {
    LocationResponseDTO.LocationFindOneDTO findOne(Long userId);
    LocationResponseDTO.LocationFindAllDTO findAll(Long userId);
}
