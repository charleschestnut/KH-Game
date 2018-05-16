package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.ReportUpdate;

public interface ReportUpdateRepository extends JpaRepository<ReportUpdate, Integer> {
	
	@Query("select ru from ReportUpdate ru where ru IN (select rupd from Report r join r.reportUpdates rupd where r.id=?1)")
	Collection<ReportUpdate> getReportUpdatesByReportId(int reportId);
	

}
