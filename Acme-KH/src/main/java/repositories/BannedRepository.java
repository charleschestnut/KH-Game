
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Banned;

public interface BannedRepository extends JpaRepository<Banned, Integer> {

	@Query("select b from Banned b where b.actor.id=?1 and b.isValid=1")
	public Banned findBanned(int actorId);

}
