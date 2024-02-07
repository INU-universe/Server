package universe.universe.entitiy.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
public class Location {
    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Long id;
    private double latitude;
    private double longitude;

    @LastModifiedDate
    private LocalDateTime updatedTime;
}
