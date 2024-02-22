package universe.universe.dto.friend;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.friend.Friend;
import universe.universe.entitiy.friend.FriendRequest;
import universe.universe.entitiy.friend.FriendStatus;
import universe.universe.entitiy.user.User;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class FriendResponseDTO {
    @Setter
    @Getter
    public static class FriendFindOneDTO {
        private Long friendId;
        private String friendImg;
        private String friendName;
        private FriendStatus friendStatus;
        private LocalDateTime schoolDate;
        public FriendFindOneDTO() {
        }
        public FriendFindOneDTO(Long friendId, String friendImg, String friendName, FriendStatus friendStatus, LocalDateTime schoolDate) {
            this.friendId = friendId;
            this.friendImg = friendImg;
            this.friendName = friendName;
            this.friendStatus = friendStatus;
            this.schoolDate = schoolDate;
        }
        public FriendFindOneDTO(Friend friend) {
            this.friendId = friend.getToUser().getId();
            this.friendImg = friend.getToUser().getUserImg();
            this.friendName = friend.getToUser().getUserName();
            this.friendStatus = friend.getFriendStatus();
            this.schoolDate = friend.getToUser().getSchoolDate();
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
    @Setter
    @Getter
    public static class FriendToggleDTO {
        private Long id;
        private Long toUserId;
        private Long fromUserId;
        public FriendToggleDTO(Friend friend) {
            this.id = friend.getId();
            this.toUserId = friend.getToUser().getId();
            this.fromUserId = friend.getFromUser().getId();
        }
    }
}
