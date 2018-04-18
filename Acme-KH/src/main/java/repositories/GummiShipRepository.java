package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.GummiShip;

public interface GummiShipRepository extends JpaRepository<GummiShip, Integer> {
	

}
