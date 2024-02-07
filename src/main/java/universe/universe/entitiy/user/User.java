package universe.universe.entitiy.user;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.LastModifiedDate;
import universe.universe.entitiy.location.Location;

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

    @Enumerated(EnumType.STRING)
    private Status userStatus;

    @LastModifiedDate
    private LocalDateTime schoolDate; // 등교 시간

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Location location;
}
