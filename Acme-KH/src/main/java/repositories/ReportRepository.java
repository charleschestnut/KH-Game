package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
	
	@Query("select r from Report r where r.keybladeWielder.id = ?1")
	Collection<Report> findReportsByPlayer(int playerId);
	
	@Query("select r from Report r join r.reportUpdates ru where ru IN (select rupd from ReportUpdate rupd where rupd.id=?1)")
	Report findReportsByReportUpdate(int reportUpdateId);

}
