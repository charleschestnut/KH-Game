package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LivelihoodRepository;
import domain.Livelihood;

@Service
@Transactional
public class LivelihoodService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private LivelihoodRepository LivelihoodRepository;

	// CRUD methods
	
	public Livelihood create(){
		Livelihood Livelihood;
		
		Livelihood = new Livelihood();
		
		return Livelihood;
	}
	
	public Livelihood save(Livelihood Livelihood){
		Assert.notNull(Livelihood);
		
		Livelihood saved;
		
		saved = LivelihoodRepository.save(Livelihood);
		
		return saved;
	}
	
	public Livelihood findOne(int LivelihoodId){
		Assert.notNull(LivelihoodId);
		
		Livelihood Livelihood;
		
		Livelihood = LivelihoodRepository.findOne(LivelihoodId);
		
		return Livelihood;
	}
	
	public Collection<Livelihood> findAll(){
		Collection<Livelihood> Livelihoods;
		
		Livelihoods = LivelihoodRepository.findAll();
		
		return Livelihoods;
	}
	
	public void delete(Livelihood Livelihood){
		Assert.notNull(Livelihood);
		
		LivelihoodRepository.delete(Livelihood);
	}

}