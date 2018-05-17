package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReportUpdateRepository;
import security.Authority;
import domain.Actor;
import domain.Administrator;
import domain.ContentManager;
import domain.Defense;
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
	@Autowired
	private Validator			validator;

	// CRUD methods
	
	public ReportUpdate create(){
		ReportUpdate reportUpdate;
		
		reportUpdate = new ReportUpdate();
		
		return reportUpdate;
	}
	
	public ReportUpdate save(ReportUpdate reportUpdate, Integer reportId){
		Assert.notNull(reportUpdate);
		Report report;
		ReportUpdate saved;
		
		report = reportService.findOne(reportId);
		Assert.isTrue(report.getStatus() != ReportStatus.RESOLVED, "error.message.noUpdate");
		
		saved = ReportUpdateRepository.save(reportUpdate);
		
		
		
		
		if(reportUpdate.getId() == 0){
			report.getReportUpdates().add(saved);
		}
		
		report.setStatus(saved.getStatus());
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
	
	public void markSuspicious(ReportUpdate reportUpdate){
		Assert.notNull(reportUpdate);
		
		reportUpdate.setIsSuspicious(true);
		ReportUpdateRepository.save(reportUpdate);
	}
	
	public Collection<ReportUpdate> getSuspiciousReportUpdates(){
		Assert.isTrue(actorService.getPrincipalAuthority().equals(Authority.ADMIN));
		
		return ReportUpdateRepository.getSuspiciousReportUpdates();
	}
	
	public ReportUpdate reconstruct(ReportUpdate reportUpdate, final BindingResult binding) {
		final ReportUpdate original = this.findOne(reportUpdate.getId());
		Actor actor;
		
		actor = actorService.findByPrincipal();
		
		if (reportUpdate.getId() == 0) {
			if(actorService.getPrincipalAuthority().equals("ADMIN")){
				reportUpdate.setAdministrator((Administrator) actor);
				reportUpdate.setGameMaster(null);
			}else if(actorService.getPrincipalAuthority().equals("GM")){
				reportUpdate.setGameMaster((GameMaster) actor);
				reportUpdate.setAdministrator(null);
			}
			
			reportUpdate.setIsSuspicious(false);

		} else {
			if(original.getGameMaster() != null){
				reportUpdate.setGameMaster(original.getGameMaster());
				reportUpdate.setAdministrator(null);
			}else if(original.getAdministrator() != null){
				reportUpdate.setAdministrator(original.getAdministrator());
				reportUpdate.setGameMaster(null);
			}
			
			reportUpdate.setIsSuspicious(original.getIsSuspicious());
			
		}
		
		reportUpdate.setDate(new Date(System.currentTimeMillis()-1000));
		
		this.validator.validate(reportUpdate, binding);

		return reportUpdate;
	}

}