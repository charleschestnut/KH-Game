package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BattleRepository;
import domain.Battle;

@Service
@Transactional
public class BattleService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BattleRepository BattleRepository;

	// CRUD methods
	
	public Battle create(){
		Battle Battle;
		
		Battle = new Battle();
		
		return Battle;
	}
	
	public Battle save(Battle Battle){
		Assert.notNull(Battle);
		
		Battle saved;
		
		saved = BattleRepository.save(Battle);
		
		return saved;
	}
	
	public Battle findOne(int BattleId){
		Assert.notNull(BattleId);
		
		Battle Battle;
		
		Battle = BattleRepository.findOne(BattleId);
		
		return Battle;
	}
	
	public Collection<Battle> findAll(){
		Collection<Battle> Battles;
		
		Battles = BattleRepository.findAll();
		
		return Battles;
	}
	
	public void delete(Battle Battle){
		Assert.notNull(Battle);
		
		BattleRepository.delete(Battle);
	}

}