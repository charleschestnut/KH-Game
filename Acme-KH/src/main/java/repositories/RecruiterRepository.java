package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Recruiter;

public interface RecruiterRepository extends JpaRepository<Recruiter, Integer> {
	

}
