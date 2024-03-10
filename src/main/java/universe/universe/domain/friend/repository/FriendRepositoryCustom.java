package universe.universe.domain.friend.repository;

import universe.universe.domain.friend.dto.FriendResponseDTO;

public interface FriendRepositoryCustom {
    FriendResponseDTO.FriendFindAllDTO findAll(Long userId);
    FriendResponseDTO.FriendFindInSchoolDTO findInSchool(Long userId);
}
