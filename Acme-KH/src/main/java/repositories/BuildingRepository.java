
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer> {

	@Query("select b from Building b where b.contentManager.id=?1")
	Collection<Building> getMyCreatedBuildings(Integer cmId);

	@Query("select b from Building b where b.isFinal=1")
	Collection<Building> getAvailableBuildings();

}
