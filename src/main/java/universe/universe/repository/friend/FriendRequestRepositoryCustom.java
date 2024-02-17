package universe.universe.repository.friend;

import universe.universe.dto.friend.FriendRequestResponseDTO;

public interface FriendRequestRepositoryCustom {
    FriendRequestResponseDTO.FriendRequestFindAllDTO friendRequestFindAll(Long userId);
}
