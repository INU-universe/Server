package universe.universe.entitiy.user;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import universe.universe.entitiy.base.BaseEntity;
import universe.universe.entitiy.chatRoom.ChatRoomRelationShip;
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
    private String userImg;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @LastModifiedDate
    private LocalDateTime schoolDate; // 등교 시간

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Location location;

    @OneToMany(mappedBy = "user")
    private List<ChatRoomRelationShip> chatRoomRelationShips = new ArrayList<>();

//    @OneToMany(mappedBy = "fromUser")
//    private List<FriendRelationShip> sentFriendRequests = new ArrayList<>();
//
//    @OneToMany(mappedBy = "toUser")
//    private List<FriendRelationShip> receivedFriendRequests = new ArrayList<>();

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

    /** ======================== 생성ㅔㅕ ======================== **/
    public User() {
    }

    public User(String userEmail, String userPassword, String userName) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
    }
}
