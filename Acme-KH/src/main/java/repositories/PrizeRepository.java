
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Prize;

public interface PrizeRepository extends JpaRepository<Prize, Integer> {

	@Query("select p from Prize p where p.keybladeWielder.id=?1 AND p.date > CURRENT_TIMESTAMP")
	Collection<Prize> getPrizeFromKeybladeWielder(Integer playerId);

	@Query("select p from Prize p where p.keybladeWielder.id=?1 AND p.date < CURRENT_TIMESTAMP")
	Collection<Prize> getTrashPrizeFromKeybladeWielder(Integer playerId);

}
