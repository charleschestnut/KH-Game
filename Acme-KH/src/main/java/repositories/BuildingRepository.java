package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
	

}
