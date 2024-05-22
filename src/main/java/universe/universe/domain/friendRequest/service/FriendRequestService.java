package universe.universe.domain.friendRequest.service;

import universe.universe.domain.friendRequest.dto.FriendRequestResponseDTO;

public interface FriendRequestService {
    // 친구 초대 링크 보내기
    FriendRequestResponseDTO.FriendRequestGetURLDTO getURL(String userEmail);
    // 친구 초대 링크 수락
    FriendRequestResponseDTO.FriendRequestAcceptURLDTO acceptURL(String userEmail, String token);
}
