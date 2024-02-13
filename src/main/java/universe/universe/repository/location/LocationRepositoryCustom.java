package universe.universe.repository.location;

import universe.universe.dto.location.LocationResponseDTO;

public interface LocationRepositoryCustom {
    LocationResponseDTO.LocationFindDTO find(Long userId);
}
