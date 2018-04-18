package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DefenseRepository;
import domain.Defense;

@Service
@Transactional
public class DefenseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private DefenseRepository DefenseRepository;

	// CRUD methods
	
	public Defense create(){
		Defense Defense;
		
		Defense = new Defense();
		
		return Defense;
	}
	
	public Defense save(Defense Defense){
		Assert.notNull(Defense);
		
		Defense saved;
		
		saved = DefenseRepository.save(Defense);
		
		return saved;
	}
	
	public Defense findOne(int DefenseId){
		Assert.notNull(DefenseId);
		
		Defense Defense;
		
		Defense = DefenseRepository.findOne(DefenseId);
		
		return Defense;
	}
	
	public Collection<Defense> findAll(){
		Collection<Defense> Defenses;
		
		Defenses = DefenseRepository.findAll();
		
		return Defenses;
	}
	
	public void delete(Defense Defense){
		Assert.notNull(Defense);
		
		DefenseRepository.delete(Defense);
	}

}