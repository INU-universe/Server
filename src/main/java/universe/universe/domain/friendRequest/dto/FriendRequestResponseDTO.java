package universe.universe.domain.friendRequest.dto;

import lombok.Getter;
import lombok.Setter;
import universe.universe.domain.user.entity.User;

public class FriendRequestResponseDTO {
    @Setter
    @Getter
    public static class FriendRequestGetURLDTO {
        private String FriendRequestURL;
    }
    @Setter
    @Getter
    public static class FriendRequestAcceptURLDTO {
        private Long toUserId;
        private Long fromUserId;
        public FriendRequestAcceptURLDTO(User fromUser, User toUser) {
            this.toUserId = fromUser.getId();
            this.fromUserId = toUser.getId();
        }
    }
}
