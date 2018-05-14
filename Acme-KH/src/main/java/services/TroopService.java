package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TroopRepository;
import domain.Materials;
import domain.Recruited;
import domain.Recruiter;
import domain.Troop;

@Service
@Transactional
public class TroopService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TroopRepository TroopRepository;
	
	@Autowired
	private BuiltService builtService;

	// CRUD methods
	
	public Troop create(Recruiter recruiter){
		Troop troop;
		troop = new Troop();
		Materials cost = new Materials();
		
		troop.setAttack(0);
		troop.setDefense(0);
		troop.setName("");
		troop.setTimeToRecruit(0);
		troop.setRecruiter(recruiter);
		troop.setRecruiterRequiredLvl(0);
		
		cost.setGummiCoal(0);
		cost.setMunny(0);
		cost.setMytrhil(0);
		
		troop.setCost(cost);
		
		return troop;
	}
	
	public Troop save(Troop troop){
		
		Troop saved = TroopRepository.save(troop);
		
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