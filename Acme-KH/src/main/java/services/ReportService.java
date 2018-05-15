package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.KeybladeWielder;
import domain.Report;
import domain.ReportStatus;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportRepository reportRepository;
	@Autowired
	private ActorService		actorService;

	// CRUD methods
	
	public Report create(){
		Report report;
		
		report = new Report();
		
		report.setPhotos(new ArrayList<String>());
		report.setDate(new Date(System.currentTimeMillis()-1000));
		report.setKeybladeWielder((KeybladeWielder) actorService.findByPrincipal());
		report.setStatus(ReportStatus.ONHOLD);
		
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
			Assert.isTrue(actorService.getPrincipalAuthority().equals("PLAYER") || actorService.getPrincipalAuthority().equals("GM"));
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

}