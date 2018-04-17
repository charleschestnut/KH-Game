package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Coordinates;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Integer> {
	

}
