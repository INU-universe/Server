package universe.universe.entitiy.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import universe.universe.entitiy.base.BaseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
public class Location extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Long id;
    private double latitude;
    private double longitude;

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
