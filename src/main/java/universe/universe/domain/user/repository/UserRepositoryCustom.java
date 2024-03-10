package universe.universe.domain.user.repository;

import universe.universe.domain.user.dto.UserResponseDTO;

public interface UserRepositoryCustom {
    UserResponseDTO.UserFindDTO findOne(String userEmail);
    void delete(Long userId);
}
