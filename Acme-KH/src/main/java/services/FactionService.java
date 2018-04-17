package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FactionRepository;
import domain.Faction;

@Service
@Transactional
public class FactionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FactionRepository FactionRepository;

	// CRUD methods
	
	public Faction create(){
		Faction Faction;
		
		Faction = new Faction();
		
		return Faction;
	}
	
	public Faction save(Faction Faction){
		Assert.notNull(Faction);
		
		Faction saved;
		
		saved = FactionRepository.save(Faction);
		
		return saved;
	}
	
	public Faction findOne(int FactionId){
		Assert.notNull(FactionId);
		
		Faction Faction;
		
		Faction = FactionRepository.findOne(FactionId);
		
		return Faction;
	}
	
	public Collection<Faction> findAll(){
		Collection<Faction> Factions;
		
		Factions = FactionRepository.findAll();
		
		return Factions;
	}
	
	public void delete(Faction Faction){
		Assert.notNull(Faction);
		
		FactionRepository.delete(Faction);
	}

}