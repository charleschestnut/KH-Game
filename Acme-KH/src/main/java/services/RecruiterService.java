package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecruiterRepository;
import domain.Recruiter;

@Service
@Transactional
public class RecruiterService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RecruiterRepository RecruiterRepository;

	// CRUD methods
	
	public Recruiter create(){
		Recruiter Recruiter;
		
		Recruiter = new Recruiter();
		
		return Recruiter;
	}
	
	public Recruiter save(Recruiter Recruiter){
		Assert.notNull(Recruiter);
		
		Recruiter saved;
		
		saved = RecruiterRepository.save(Recruiter);
		
		return saved;
	}
	
	public Recruiter findOne(int RecruiterId){
		Assert.notNull(RecruiterId);
		
		Recruiter Recruiter;
		
		Recruiter = RecruiterRepository.findOne(RecruiterId);
		
		return Recruiter;
	}
	
	public Collection<Recruiter> findAll(){
		Collection<Recruiter> Recruiters;
		
		Recruiters = RecruiterRepository.findAll();
		
		return Recruiters;
	}
	
	public void delete(Recruiter Recruiter){
		Assert.notNull(Recruiter);
		
		RecruiterRepository.delete(Recruiter);
	}

}