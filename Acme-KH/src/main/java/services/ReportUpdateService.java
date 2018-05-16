package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportUpdateRepository;
import domain.Actor;
import domain.Administrator;
import domain.GameMaster;
import domain.Report;
import domain.ReportStatus;
import domain.ReportUpdate;

@Service
@Transactional
public class ReportUpdateService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportUpdateRepository ReportUpdateRepository;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ReportService		reportService;

	// CRUD methods
	
	public ReportUpdate create(){
		ReportUpdate reportUpdate;
		Actor actor;
		
		reportUpdate = new ReportUpdate();
		actor = actorService.findByPrincipal();
		
		reportUpdate.setDate(new Date(System.currentTimeMillis()-1000));
		
		if(actorService.getPrincipalAuthority().equals("ADMIN")){
			reportUpdate.setAdministrator((Administrator) actor);
			reportUpdate.setGameMaster(null);
		}else if(actorService.getPrincipalAuthority().equals("GM")){
			reportUpdate.setGameMaster((GameMaster) actor);
			reportUpdate.setAdministrator(null);
		}
		
		return reportUpdate;
	}
	
	public ReportUpdate save(ReportUpdate reportUpdate, Integer reportId, ReportStatus status){
		Assert.notNull(reportUpdate);
		Report report;
		ReportUpdate saved;
		
		report = reportService.findOne(reportId);
		Assert.isTrue(report.getStatus() != ReportStatus.RESOLVED, "error.message.noUpdate");
		
		saved = ReportUpdateRepository.save(reportUpdate);
		
		report.getReportUpdates().add(saved);
		report.setStatus(status);
		
		reportService.save(report);
		
		
		return saved;
	}
	
	public ReportUpdate findOne(int ReportUpdateId){
		Assert.notNull(ReportUpdateId);
		
		ReportUpdate ReportUpdate;
		
		ReportUpdate = ReportUpdateRepository.findOne(ReportUpdateId);
		
		return ReportUpdate;
	}
	
	public Collection<ReportUpdate> findAll(){
		Collection<ReportUpdate> ReportUpdates;
		
		ReportUpdates = ReportUpdateRepository.findAll();
		
		return ReportUpdates;
	}
	
	public void delete(ReportUpdate ReportUpdate){
		Assert.notNull(ReportUpdate);
		
		ReportUpdateRepository.delete(ReportUpdate);
	}
	
	//Other business methods
	
	public Collection<ReportUpdate> getReportUpdatesByReportId(int reportId){
		return ReportUpdateRepository.getReportUpdatesByReportId(reportId);
	}

}