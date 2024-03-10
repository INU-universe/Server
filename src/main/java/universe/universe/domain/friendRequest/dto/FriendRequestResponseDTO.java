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
//    @Setter
//    @Getter
//    public static class FriendRequestToggleDTO {
//        private Long id;
//        private Long toUserId;
//        private Long fromUserId;
//        public FriendRequestToggleDTO(FriendRequest friendRequest) {
//            this.id = friendRequest.getId();
//            this.toUserId = friendRequest.getToUser().getId();
//            this.fromUserId = friendRequest.getFromUser().getId();
//        }
//    }
//    @Setter
//    @Getter
//    public static class FriendRequestAcceptDTO {
//        private Long id;
//        private Long toUserId;
//        private Long fromUserId;
//        public FriendRequestAcceptDTO(FriendRequest friendRequest) {
//            this.id = friendRequest.getId();
//            this.toUserId = friendRequest.getToUser().getId();
//            this.fromUserId = friendRequest.getFromUser().getId();
//        }
//    }
//    @Setter
//    @Getter
//    public static class FriendRequestRejectDTO {
//        private Long id;
//        private Long toUserId;
//        private Long fromUserId;
//        public FriendRequestRejectDTO(FriendRequest friendRequest) {
//            this.id = friendRequest.getId();
//            this.toUserId = friendRequest.getToUser().getId();
//            this.fromUserId = friendRequest.getFromUser().getId();
//        }
//    }
//    @Setter
//    @Getter
//    public static class FriendRequestFindOneDTO {
//        private Long userId;
//        private String userImg;
//        private String userName;
//        public FriendRequestFindOneDTO() {
//        }
//        public FriendRequestFindOneDTO(Long userId, String userImg, String userName) {
//            this.userId = userId;
//            this.userImg = userImg;
//            this.userName = userName;
//        }
//        public FriendRequestFindOneDTO(FriendRequest friendRequest) {
//            this.userId = friendRequest.getToUser().getId();
//            this.userImg = friendRequest.getToUser().getUserImg();
//            this.userName = friendRequest.getToUser().getUserName();
//        }
//    }
//    @Setter
//    @Getter
//    public static class FriendRequestFindAllDTO {
//        private Long userId;
//        private List<FriendRequestResponseDTO.FriendRequestFindOneDTO> friendList;
//        public FriendRequestFindAllDTO(Long userId, List<FriendRequestResponseDTO.FriendRequestFindOneDTO> friendList) {
//            this.userId= userId;
//            this.friendList = friendList;
//        }
//    }
}
