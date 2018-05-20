
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.KeybladeWielder;

public interface KeybladeWielderRepository extends JpaRepository<KeybladeWielder, Integer> {

	@Query("select i.keybladeWielder from Invitation i where (i.invitationStatus='ACCEPTED' and i.organization.id=?1)")
	Collection<KeybladeWielder> findMembersOfOrganization(int organizationId);

	//Dashboard

	@Query("select 1.0*(select count(k)/(select count(u) from KeybladeWielder u) from KeybladeWielder k where k.faction.id=f.id) from Faction f")
	Collection<Double> ratioOfUserPerFaction();

	@Query("select k from KeybladeWielder k order by wins desc")
	Collection<KeybladeWielder> getTopWinsPlayers();

	@Query("select k from KeybladeWielder k where k.wins>10 order by (wins/(wins+loses)) desc")
	Collection<KeybladeWielder> getTopWinRatioPlayers();

	@Query("select k from KeybladeWielder k order by materials.munny desc")
	Collection<KeybladeWielder> getTopMunnyPlayers();

	@Query("select k.materials.mytrhil from KeybladeWielder k order by materials.mytrhil desc")
	Collection<KeybladeWielder> getTopMythrilPlayers();

	@Query("select avg(1.0*(k.wins/(k.loses+k.wins))) from KeybladeWielder k")
	Double avgOfWinRatio();

}
