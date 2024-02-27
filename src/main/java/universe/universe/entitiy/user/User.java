package universe.universe.entitiy.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import universe.universe.entitiy.base.BaseEntity;
import universe.universe.entitiy.chatRoom.ChatRoomRelation;
import universe.universe.entitiy.friend.FriendStatus;
import universe.universe.entitiy.location.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String role = "ROLE_USER";
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userImg = "img";

    /** Oauth2 Login **/
//    private String provider;
//    private String providerId;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.NOT_SCHOOL;

    @LastModifiedDate
    private LocalDateTime schoolDate; // 등교 시간

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Location location;

    /** ======================== 메소드 ======================== **/
    public void updateRole(String role) {
        this.role = role;
    }
    public void updateUserImg(String userImg) {
        this.userImg = userImg;
    }

    public List<String> getRoleList() {
        if (this.role.length() > 0) {
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }

    /** ======================== 생성자 ======================== **/
    public User() {
    }

    public User(String userEmail, String userPassword, String userName) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.location = new Location();
    }

    @Builder
    public User(String userEmail, String userName, String userPassword, String role) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
    }
    @Builder
    public User(String userEmail, String userName, String userPassword, String role, String provider, String providerId) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
//        this.provider = provider;
//        this.providerId = providerId;
    }
}
