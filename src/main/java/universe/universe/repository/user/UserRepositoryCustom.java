package universe.universe.repository.user;

import universe.universe.dto.user.UserResponseDTO;

public interface UserRepositoryCustom {
    UserResponseDTO.UserFindDTO findOne(String userEmail);
}
