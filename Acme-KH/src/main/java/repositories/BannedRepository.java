
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Actor;
import domain.Banned;

public interface BannedRepository extends JpaRepository<Banned, Integer> {

	@Query("select count(b) from Banned b where b.actor.id=?1 and b.isValid=1")
	public Integer findBansByActor(int actorId);

	@Query("select b from Banned b where b.actor.id=?1 and b.isValid=1")
	public Banned findToUnbanByActor(int actorId);

	@Query("select b.actor from Banned b where b.isValid=1")
	public Collection<Actor> findAllBannedUsers();

	@Query("select b.actor from Banned b where b.isValid=1")
	public Page<Actor> findAllBannedUsers(Pageable pageables);

}
