package universe.universe.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.user.User;

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
}