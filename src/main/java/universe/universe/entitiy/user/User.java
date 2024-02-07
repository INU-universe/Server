package universe.universe.entitiy.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userImg;
    private Status userStatus;
    private LocalDateTime schoolDate; // 등교 시간
}
