package universe.universe.domain.location.repository;

import universe.universe.domain.location.dto.LocationResponseDTO;

public interface LocationRepositoryCustom {
    LocationResponseDTO.LocationFindOneDTO findOne(Long userId);
    LocationResponseDTO.LocationFindAllDTO notFavoriteFindAll(Long userId);
    LocationResponseDTO.LocationFindAllDTO favoriteFindAll(Long userId);
}
