package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.ReportUpdate;

public interface ReportUpdateRepository extends JpaRepository<ReportUpdate, Integer> {
	
	@Query("select ru from ReportUpdate ru where ru IN (select rupd from Report r join r.reportUpdates rupd where r.id=?1)")
	Collection<ReportUpdate> getReportUpdatesByReportId(int reportId);
	
	@Query("select ru from ReportUpdate ru where ru.isSuspicious = true")
	Collection<ReportUpdate> getSuspiciousReportUpdates();
	
	@Query("select ru from ReportUpdate ru where ru IN (select rupd from Report r join r.reportUpdates rupd where r.id=?1) and (ru.administrator.id = ?2 or ru.gameMaster.id = ?2)")
	Collection<ReportUpdate> getReportUpdatesByActorId(int reportId, int actorId);
	
	@Query("select ru from ReportUpdate ru where ru IN (select rupd from Report r join r.reportUpdates rupd where r.id=?1) and  ru.status = 'RESOLVED'")
	Collection<ReportUpdate> getResolvedReportUpdates(int reportId);
	

}
