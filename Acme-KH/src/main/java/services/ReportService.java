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
import domain.Report;

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
		
		return report;
	}
	
	public Report save(Report report){
		Assert.notNull(report);
		
		Report saved;
		
		if(report.getId() == 0){
			Assert.isTrue(actorService.getPrincipalAuthority().equals("PLAYER"));
			
			if(report.getPhotos() != null){
				
				UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
				
				for(String url : report.getPhotos()){
					Assert.isTrue(urlValidator.isValid(url), "org.hibernate.validator.constraints.URL.message");
				}
			}
			
			report.setDate(new Date(System.currentTimeMillis()-1000));
			
			
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

}