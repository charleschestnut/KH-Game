package services;

import java.util.Collection;

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
	private ReportRepository ReportRepository;

	// CRUD methods
	
	public Report create(){
		Report Report;
		
		Report = new Report();
		
		return Report;
	}
	
	public Report save(Report Report){
		Assert.notNull(Report);
		
		Report saved;
		
		saved = ReportRepository.save(Report);
		
		return saved;
	}
	
	public Report findOne(int ReportId){
		Assert.notNull(ReportId);
		
		Report Report;
		
		Report = ReportRepository.findOne(ReportId);
		
		return Report;
	}
	
	public Collection<Report> findAll(){
		Collection<Report> Reports;
		
		Reports = ReportRepository.findAll();
		
		return Reports;
	}
	
	public void delete(Report Report){
		Assert.notNull(Report);
		
		ReportRepository.delete(Report);
	}

}