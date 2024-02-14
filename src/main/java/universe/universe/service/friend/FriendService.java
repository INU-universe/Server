package universe.universe.service.friend;

import universe.universe.dto.friend.FriendResponseDTO;

public interface FriendService {
    FriendResponseDTO.FriendFindAllDTO findAll(String userEmail);
}
