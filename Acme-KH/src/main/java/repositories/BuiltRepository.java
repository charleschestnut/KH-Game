package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Built;

public interface BuiltRepository extends JpaRepository<Built, Integer> {
	

}
