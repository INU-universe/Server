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

        // DTO를 User 엔티티로 변환하는 메소드
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