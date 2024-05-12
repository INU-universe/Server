package universe.universe.domain.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.domain.user.entity.User;

@Data
public class UserRequestDTO {
    @Data
    public static class UserJoinDTO {
        private String userEmail;
        private String userPassword;
        private String userName;
        public User toEntity() {
            return new User(this.userEmail, this.userPassword, this.userName);
        }
    }

    @Data
    public static class UserUpdateDTO {
        private String userEmail;
        private String userImg;
    }

    @Data
    public static class UserUpdateSchoolDTO {
        private String userStatus;
    }

    @Data
    public static class UserUpdateEmotionDTO {
        private String userEmotion;
    }

    @Data
    public static class UserUpdateNotSchoolDTO {
        private String userStatus;
    }
}