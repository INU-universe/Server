package universe.universe.service.friend;

import universe.universe.dto.friend.FriendRelationShipResponseDTO;

public interface FriendRelationShipService {
    // 친구 신청
    FriendRelationShipResponseDTO.FriendRelationShipToggleDTO toggle(String userEmail, Long toUserId);

    // 친구 수락 / 거절
    // 친구 목록 조회
//    List<FriendResponseDTO.FriendFindAllDTO> findAll(String userEmail, Long toUserId);

    // 친구 추천
}
