package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequirementRepository;
import domain.Requirement;

@Service
@Transactional
public class RequirementService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RequirementRepository RequirementRepository;

	// CRUD methods
	
	public Requirement create(){
		Requirement Requirement;
		
		Requirement = new Requirement();
		
		return Requirement;
	}
	
	public Requirement save(Requirement Requirement){
		Assert.notNull(Requirement);
		
		Requirement saved;
		
		saved = RequirementRepository.save(Requirement);
		
		return saved;
	}
	
	public Requirement findOne(int RequirementId){
		Assert.notNull(RequirementId);
		
		Requirement Requirement;
		
		Requirement = RequirementRepository.findOne(RequirementId);
		
		return Requirement;
	}
	
	public Collection<Requirement> findAll(){
		Collection<Requirement> Requirements;
		
		Requirements = RequirementRepository.findAll();
		
		return Requirements;
	}
	
	public void delete(Requirement Requirement){
		Assert.notNull(Requirement);
		
		RequirementRepository.delete(Requirement);
	}

}