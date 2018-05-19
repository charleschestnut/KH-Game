
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Troop;

public interface TroopRepository extends JpaRepository<Troop, Integer> {

	@Query("select t from Troop t where t.recruiter.id=?1")
	Collection<Troop> getTroopsFromRecruiter(Integer recruiterId);

	@Query("select r.troop from Recruited r where r.storageBuilding.id=?1 and r.troop!=null")
	Collection<Troop> getStoragedTroops(Integer builtId);

}
