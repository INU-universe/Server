package universe.universe.domain.friend.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.domain.friend.entity.Friend;
import universe.universe.domain.friend.entity.FriendStatus;
import universe.universe.domain.user.entity.UserStatus;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class FriendResponseDTO {
    @Setter
    @Getter
    public static class FriendFindOneDTO {
        private Long id;
        private Long friendId;
        private String friendImg;
        private String friendName;
        private FriendStatus friendStatus;
        private UserStatus userStatus;
        private LocalDateTime schoolDate;
        public FriendFindOneDTO() {
        }
        public FriendFindOneDTO(Long id, Long friendId, String friendImg, String friendName, FriendStatus friendStatus, UserStatus userStatus ,LocalDateTime schoolDate) {
            this.id = id;
            this.friendId = friendId;
            this.friendImg = friendImg;
            this.friendName = friendName;
            this.friendStatus = friendStatus;
            this.userStatus = userStatus;
            this.schoolDate = schoolDate;
        }
        public FriendFindOneDTO(Friend friend) {
            this.id = friend.getId();
            this.friendId = friend.getToUser().getId();
            this.friendImg = friend.getToUser().getUserImg();
            this.friendName = friend.getToUser().getUserName();
            this.friendStatus = friend.getFriendStatus();
            this.userStatus = friend.getToUser().getUserStatus();
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
    public static class FriendFindInSchoolDTO {
        private Long userId;
        private List<FriendResponseDTO.FriendFindOneDTO> friendList;
        public FriendFindInSchoolDTO(Long userId, List<FriendFindOneDTO> friendList) {
            this.userId= userId;
            this.friendList = friendList;
        }
    }
}
