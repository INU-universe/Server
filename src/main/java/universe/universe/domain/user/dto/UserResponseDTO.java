package universe.universe.domain.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.entity.UserStatus;

@Data
public class UserResponseDTO {
    @Setter
    @Getter
    public static class UserJoinDTO {
        private Long id;
        private String userEmail;
        private String userPassword;
        private String userName;

        public UserJoinDTO(User user) {
            this.id = user.getId();
            this.userEmail = user.getUserEmail();
            this.userPassword = user.getUserPassword();
            this.userName = user.getUserName();
        }
    }

    @Setter
    @Getter
    public static class UserFindDTO {
        private Long id;
        private String userEmail;
        private String userName;
        private String role;
        private String userImg;
        private UserStatus userStatus;

        public UserFindDTO(Long id, String userEmail, String userName, String role, String userImg, UserStatus userStatus) {
            this.id = id;
            this.userEmail = userEmail;
            this.userName = userName;
            this.role = role;
            this.userImg = userImg;
            this.userStatus = userStatus;
        }
    }

    @Data
    public static class UserUpdateSchoolDTO {
        private Long id;
        private String userEmail;
        private String userName;
        private String role;
        private String userImg;
        private UserStatus userStatus;
        public UserUpdateSchoolDTO(User user) {
            this.id = user.getId();
            this.userEmail = user.getUserEmail();
            this.userName = user.getUserName();
            this.userImg = user.getUserImg();
            this.role = user.getRole();
            this.userStatus = user.getUserStatus();
        }
    }

    @Data
    public static class userUpdateNotSchoolDTO {
        private Long id;
        private String userEmail;
        private String userName;
        private String role;
        private String userImg;
        private UserStatus userStatus;
        public userUpdateNotSchoolDTO(User user) {
            this.id = user.getId();
            this.userEmail = user.getUserEmail();
            this.userName = user.getUserName();
            this.userImg = user.getUserImg();
            this.role = user.getRole();
            this.userStatus = user.getUserStatus();
        }
    }

    @Data
    public static class UserUpdateDTO {
        private Long id;
        private String userEmail;
        private String userName;
        private String role;
        private String userImg;
        private UserStatus userStatus;
        public UserUpdateDTO(User user) {
            this.id = user.getId();
            this.userEmail = user.getUserEmail();
            this.userName = user.getUserName();
            this.userImg = user.getUserImg();
            this.role = user.getRole();
            this.userStatus = user.getUserStatus();
        }
    }
    @Data
    public static class UserDeleteDTO {
        private Long id;
        private String userEmail;
        private String userName;
        private String role;
        private String userImg;
        private UserStatus userStatus;
        public UserDeleteDTO(User user) {
            this.id = user.getId();
            this.userEmail = user.getUserEmail();
            this.userName = user.getUserName();
            this.userImg = user.getUserImg();
            this.role = user.getRole();
            this.userStatus = user.getUserStatus();
        }
    }
}