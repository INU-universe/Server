package universe.universe.service.friend;

import universe.universe.dto.friend.FriendRequestResponseDTO;

public interface FriendRequestService {
    // 친구 토글
    FriendRequestResponseDTO.FriendRequestToggleDTO toggle(String userEmail, Long toUserId);

    // 친구 수락
    FriendRequestResponseDTO.FriendRequestAcceptDTO accept(String userEmail, Long friendRelationShipId);

    // 친구 거절
    FriendRequestResponseDTO.FriendRequestRejectDTO reject(String userEmail, Long friendRelationShipId);
}
