package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Requirement;

public interface RequirementRepository extends JpaRepository<Requirement, Integer> {
	

}
