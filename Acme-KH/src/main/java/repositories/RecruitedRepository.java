
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Recruited;

public interface RecruitedRepository extends JpaRepository<Recruited, Integer> {

	// @Query("select r from Recruited r where r.storageBuilding.id=?1 AND r.troop!=null")

}
