package universe.universe.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.user.User;

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
        private String profileImg;

        public UserFindDTO(Long id, String userEmail, String userName, String role, String profileImg) {
            this.id = id;
            this.userEmail = userEmail;
            this.userName = userName;
            this.role = role;
            this.profileImg = profileImg;
        }
    }

    @Setter
    @Getter
    public static class UserUpdateDTO {
        private String userEmail;
        private String userName;
        private String role;

        public UserUpdateDTO(User user) {
            this.userEmail = user.getUserEmail();
            this.userName = user.getUserName();
            this.role = user.getRole();
        }
    }
}