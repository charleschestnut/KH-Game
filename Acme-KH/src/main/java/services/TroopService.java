package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TroopRepository;
import domain.Troop;

@Service
@Transactional
public class TroopService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TroopRepository TroopRepository;

	// CRUD methods
	
	public Troop create(){
		Troop Troop;
		
		Troop = new Troop();
		
		return Troop;
	}
	
	public Troop save(Troop Troop){
		Assert.notNull(Troop);
		
		Troop saved;
		
		saved = TroopRepository.save(Troop);
		
		return saved;
	}
	
	public Troop findOne(int TroopId){
		Assert.notNull(TroopId);
		
		Troop Troop;
		
		Troop = TroopRepository.findOne(TroopId);
		
		return Troop;
	}
	
	public Collection<Troop> findAll(){
		Collection<Troop> Troops;
		
		Troops = TroopRepository.findAll();
		
		return Troops;
	}
	
	public void delete(Troop Troop){
		Assert.notNull(Troop);
		
		TroopRepository.delete(Troop);
	}

}