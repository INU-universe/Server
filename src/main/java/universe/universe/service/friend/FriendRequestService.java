package universe.universe.service.friend;

import universe.universe.dto.friend.FriendRequestResponseDTO;

public interface FriendRequestService {
    // 친구 초대 링크 보내기
    FriendRequestResponseDTO.FriendRequestSendDTO send(String userEmail);
    // 친구 토글
    FriendRequestResponseDTO.FriendRequestToggleDTO toggle(String userEmail, Long toUserId);

    // 친구 수락
    FriendRequestResponseDTO.FriendRequestAcceptDTO accept(String userEmail, Long friendRelationShipId);

    // 친구 거절
    FriendRequestResponseDTO.FriendRequestRejectDTO reject(String userEmail, Long friendRelationShipId);
    // 친구 요청 조회
    FriendRequestResponseDTO.FriendRequestFindAllDTO findAll(String userEmail);
}
