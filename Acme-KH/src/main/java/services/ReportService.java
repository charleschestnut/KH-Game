package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReportRepository;
import domain.Actor;
import domain.Administrator;
import domain.GameMaster;
import domain.KeybladeWielder;
import domain.Report;
import domain.ReportStatus;
import domain.ReportUpdate;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportRepository reportRepository;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private Validator validator;

	// CRUD methods
	
	public Report create(){
		Report report;
		
		report = new Report();
		
		return report;
	}
	
	public Report save(Report report){
		Assert.notNull(report);
		
		Report saved;
		
		if(report.getId() == 0){
			Assert.isTrue(actorService.getPrincipalAuthority().equals("PLAYER"));
			Assert.isTrue(report.getStatus().equals(ReportStatus.ONHOLD));
			
			if(report.getPhotos() != null){
				Assert.isTrue(report.getPhotos().size() <= 5, "error.message.photos.limit");
				UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
				
				for(String url : report.getPhotos()){
					Assert.isTrue(urlValidator.isValid(url), "org.hibernate.validator.constraints.URL.message");
				}
			}
		}else{
			Assert.isTrue(actorService.getPrincipalAuthority().equals("PLAYER") || actorService.getPrincipalAuthority().equals("GM")
					|| actorService.getPrincipalAuthority().equals("ADMIN"));
			Assert.isTrue(report.getStatus()!=ReportStatus.ONHOLD);
			
		}
		
		
		
		saved = reportRepository.save(report);
		
		return saved;
	}
	
	public Report findOne(int ReportId){
		Assert.notNull(ReportId);
		
		Report Report;
		
		Report = reportRepository.findOne(ReportId);
		
		return Report;
	}
	
	public Collection<Report> findAll(){
		Collection<Report> Reports;
		
		Reports = reportRepository.findAll();
		
		return Reports;
	}
	
	public void delete(Report report){
		Assert.notNull(report);
		
		reportRepository.delete(report);
	}
	
	//Other business methods
	
	public Collection<Report> findReportsByPlayer(int playerId){
		return reportRepository.findReportsByPlayer(playerId);
	}
	
	public Report findReportsByReportUpdate(int reportUpdateId){
		return reportRepository.findReportsByReportUpdate(reportUpdateId);
	}
	
	public Collection<Report> getReportsByStatus(ReportStatus status){
		return reportRepository.getReportsByStatus(status);
	}
	
	public Collection<Report> getReportsByStatusAndPlayer(ReportStatus status, int playerId){
		return reportRepository.getReportsByStatusAndPlayer(status, playerId);
	}
	
	public Report reconstruct(Report report,
			final BindingResult binding) {
		

		if(report.getId() == 0){
			Actor actor;

			actor = actorService.findByPrincipal();
			report.setDate(new Date(System.currentTimeMillis() - 1000));
			report.setKeybladeWielder((KeybladeWielder) actor);
			report.setStatus(ReportStatus.ONHOLD);
			report.setPhotos(new ArrayList<String>());
		}else{
			Report original = this.findOne(report.getId());
			
			report.setContent(original.getContent());
			report.setDate(original.getDate());
			report.setIsBug(original.getIsBug());
			report.setKeybladeWielder(original.getKeybladeWielder());
			report.setPhotos(original.getPhotos());
			report.setReportUpdates(original.getReportUpdates());
			report.setTitle(original.getTitle());
		}
		
		this.validator.validate(report, binding);

		return report;
	}

}