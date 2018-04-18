package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecruitedRepository;
import domain.Recruited;

@Service
@Transactional
public class RecruitedService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RecruitedRepository RecruitedRepository;

	// CRUD methods
	
	public Recruited create(){
		Recruited Recruited;
		
		Recruited = new Recruited();
		
		return Recruited;
	}
	
	public Recruited save(Recruited Recruited){
		Assert.notNull(Recruited);
		
		Recruited saved;
		
		saved = RecruitedRepository.save(Recruited);
		
		return saved;
	}
	
	public Recruited findOne(int RecruitedId){
		Assert.notNull(RecruitedId);
		
		Recruited Recruited;
		
		Recruited = RecruitedRepository.findOne(RecruitedId);
		
		return Recruited;
	}
	
	public Collection<Recruited> findAll(){
		Collection<Recruited> Recruiteds;
		
		Recruiteds = RecruitedRepository.findAll();
		
		return Recruiteds;
	}
	
	public void delete(Recruited Recruited){
		Assert.notNull(Recruited);
		
		RecruitedRepository.delete(Recruited);
	}

}