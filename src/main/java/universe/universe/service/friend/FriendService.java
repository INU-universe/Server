package universe.universe.service.friend;

import universe.universe.dto.friend.FriendResponseDTO;

public interface FriendService {
    FriendResponseDTO.FriendFindAllDTO findAll(String userEmail);
    FriendResponseDTO.FriendFindInSchoolDTO findInSchool(String userEmail);
    void delete(String userEmail, Long userId);
    FriendResponseDTO.FriendToggleDTO toggle(String userEmail, Long toUserId);
}
