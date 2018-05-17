package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Report;
import domain.ReportStatus;

public interface ReportRepository extends JpaRepository<Report, Integer> {
	
	@Query("select r from Report r where r.keybladeWielder.id = ?1")
	Collection<Report> findReportsByPlayer(int playerId);
	
	@Query("select r from Report r join r.reportUpdates ru where ru IN (select rupd from ReportUpdate rupd where rupd.id=?1)")
	Report findReportsByReportUpdate(int reportUpdateId);
	
	@Query("select r from Report r where r.status = ?1")
	Collection<Report> getReportsByStatus(ReportStatus status);
	
	@Query("select r from Report r where r.status = ?1 and r.keybladeWielder.id = ?2")
	Collection<Report> getReportsByStatusAndPlayer(ReportStatus status, int playerId);

}
