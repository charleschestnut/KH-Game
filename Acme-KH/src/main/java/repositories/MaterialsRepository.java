package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Materials;

public interface MaterialsRepository extends JpaRepository<Materials, Integer> {
	

}
