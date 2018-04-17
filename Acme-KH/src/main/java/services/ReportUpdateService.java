package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportUpdateRepository;
import domain.ReportUpdate;

@Service
@Transactional
public class ReportUpdateService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportUpdateRepository ReportUpdateRepository;

	// CRUD methods
	
	public ReportUpdate create(){
		ReportUpdate ReportUpdate;
		
		ReportUpdate = new ReportUpdate();
		
		return ReportUpdate;
	}
	
	public ReportUpdate save(ReportUpdate ReportUpdate){
		Assert.notNull(ReportUpdate);
		
		ReportUpdate saved;
		
		saved = ReportUpdateRepository.save(ReportUpdate);
		
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

}