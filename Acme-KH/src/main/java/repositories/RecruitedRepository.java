
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Recruited;

public interface RecruitedRepository extends JpaRepository<Recruited, Integer> {

	@Query("select r from Recruited r where r.storageBuilding.id=?1 AND r.troop!=null")
	public Collection<Recruited> getMyStoragedRecruitedTroops(Integer builtId);

	@Query("select r from Recruited r where r.storageBuilding.id=?1 AND r.troop==null")
	public Collection<Recruited> getMyStoragedRecruitedGummiShip(Integer builtId);

	@Query("select r from Recruited r where r.storageBuilding.id=?1")
	public Collection<Recruited> getMyRecruited(Integer builtId);

}
