package universe.universe.domain.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.domain.user.entity.User;

@Data
public class UserRequestDTO {
    @Setter
    @Getter
    public static class UserJoinDTO {
        private String userEmail;
        private String userPassword;
        private String userName;
        public User toEntity() {
            return new User(this.userEmail, this.userPassword, this.userName);
        }
    }

    @Setter
    @Getter
    public static class UserUpdateDTO {
        private String userEmail;
        private String userImg;
    }

    @Setter
    @Getter
    public static class UserUpdateSchoolDTO {
        private String userStatus;
    }

    @Setter
    @Getter
    public static class UserUpdateNotSchoolDTO {
        private String userStatus;
    }
}