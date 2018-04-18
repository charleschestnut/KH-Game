package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Recruited;

public interface RecruitedRepository extends JpaRepository<Recruited, Integer> {
	

}
