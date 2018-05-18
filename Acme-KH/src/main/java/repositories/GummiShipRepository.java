
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.GummiShip;

public interface GummiShipRepository extends JpaRepository<GummiShip, Integer> {

	@Query("select g from GummiShip g where g.recruiter.id=?1")
	Collection<GummiShip> getGummiShipsFromRecruiter(Integer recruiterId);

}
