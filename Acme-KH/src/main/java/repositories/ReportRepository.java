package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
	

}
