package universe.universe.repository.friend;

import universe.universe.dto.friend.FriendResponseDTO;

public interface FriendRepositoryCustom {
    FriendResponseDTO.FriendFindAllDTO findAll(Long userId);
}
