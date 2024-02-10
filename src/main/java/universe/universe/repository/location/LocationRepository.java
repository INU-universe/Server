package universe.universe.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.entitiy.location.Location;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {
}
