package universe.universe.domain.friend.service;

import universe.universe.domain.friend.dto.FriendResponseDTO;

public interface FriendService {
    FriendResponseDTO.FriendFindAllDTO findAll(String userEmail);
    FriendResponseDTO.FriendFindInSchoolDTO findInSchool(String userEmail);
    void delete(String userEmail, Long userId);
}
