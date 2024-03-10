package universe.universe.domain.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.domain.location.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {
}
