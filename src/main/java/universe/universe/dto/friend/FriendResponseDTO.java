package universe.universe.dto.friend;


import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.friend.Friend;
import universe.universe.entitiy.friend.FriendStatus;

import java.util.List;

public class FriendResponseDTO {
    @Setter
    @Getter
    public static class FriendFindOneDTO {
        private Long friendId;
        private String friendImg;
        private String friendName;
        private FriendStatus friendStatus;

        public FriendFindOneDTO(Friend friend) {
            this.friendId = friend.getToUser().getId();
            this.friendImg = friend.getToUser().getUserImg();
            this.friendName = friend.getToUser().getUserName();
            this.friendStatus = friend.getFriendStatus();
        }
    }
    @Setter
    @Getter
    public static class FriendFindAllDTO {
        private Long userId;
        private List<FriendResponseDTO.FriendFindOneDTO> friendList;

        public FriendFindAllDTO(Long userId, List<FriendFindOneDTO> friendList) {
            this.userId= userId;
            this.friendList = friendList;
        }
    }
}
