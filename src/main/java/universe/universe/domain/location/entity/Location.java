package universe.universe.domain.location.entity;

import jakarta.persistence.*;
import lombok.Getter;
import universe.universe.global.common.BaseEntity;
import universe.universe.domain.user.entity.User;

@Entity
@Getter
public class Location extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Long id;
    private double latitude;
    private double longitude;

    @OneToOne(mappedBy = "location", fetch = FetchType.LAZY)
    private User user;

    /** ======================== 메소드 ======================== **/
    public void updateLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /** ======================== 생성자 ======================== **/
    public Location() {

    }
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this. longitude = longitude;
    }
}
