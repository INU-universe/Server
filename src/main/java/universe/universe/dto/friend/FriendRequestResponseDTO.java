package universe.universe.dto.friend;

import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.friend.FriendRequest;
import universe.universe.entitiy.friend.FriendRequestStatus;

public class FriendRequestResponseDTO {
    @Setter
    @Getter
    public static class FriendRequestToggleDTO {
        private Long id;
        private Long toUserId;
        private Long fromUserId;
        private FriendRequestStatus friendRequestStatus;
        public FriendRequestToggleDTO(FriendRequest friendRequest) {
            this.id = friendRequest.getId();
            this.toUserId = friendRequest.getToUser().getId();
            this.fromUserId = friendRequest.getFromUser().getId();
            this.friendRequestStatus = friendRequest.getFriendRequestStatus();
        }
    }
    @Setter
    @Getter
    public static class FriendRequestAcceptDTO {
        private Long id;
        private Long toUserId;
        private Long fromUserId;
        private FriendRequestStatus friendRequestStatus;
        public FriendRequestAcceptDTO(FriendRequest friendRequest) {
            this.id = friendRequest.getId();
            this.toUserId = friendRequest.getToUser().getId();
            this.fromUserId = friendRequest.getFromUser().getId();
            this.friendRequestStatus = friendRequest.getFriendRequestStatus();
        }
    }
    @Setter
    @Getter
    public static class FriendRequestRejectDTO {
        private Long id;
        private Long toUserId;
        private Long fromUserId;
        private FriendRequestStatus friendRequestStatus;
        public FriendRequestRejectDTO(FriendRequest friendRequest) {
            this.id = friendRequest.getId();
            this.toUserId = friendRequest.getToUser().getId();
            this.fromUserId = friendRequest.getFromUser().getId();
            this.friendRequestStatus = friendRequest.getFriendRequestStatus();
        }
    }
}
