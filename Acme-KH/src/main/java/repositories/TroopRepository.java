package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Troop;

public interface TroopRepository extends JpaRepository<Troop, Integer> {
	

}
