package universe.universe.entitiy.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import universe.universe.entitiy.base.BaseEntity;

@Entity
@Getter
public class Location extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Long id;
    private double latitude;
    private double longitude;
}
