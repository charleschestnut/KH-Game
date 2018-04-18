package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Battle;

public interface BattleRepository extends JpaRepository<Battle, Integer> {
	

}
